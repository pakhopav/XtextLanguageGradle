package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.RuleResolverImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.*
import com.intellij.xtextLanguage.xtext.generator.visitors.ComplicatedConditionalBranchesFinder
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*
import kotlin.streams.toList

class XtextMainModel(val xtextFiles: List<XtextFile>) {
    val parserRules: List<ParserRule>
    val enumRules: List<EnumRule>
    val terminalRules: List<TerminalRule>
    val refactoredXtextParserRules: MutableList<XtextParserRule>
    val xtextRuleListEditor: RuleListEditor
    val referencesModel: XtextCrossReferencesModel
    val keywordModel: XtextKeywordModel
    lateinit var visitorGeneratorModel: VisitorGeneratorModel
    val ruleResolver = RuleResolverImpl(this)
    val bridgeModel: BridgeModel

    private val rulesWithSuperClass = mutableMapOf<String, String>()
    private var newNamesCounter = 0


    init {
        var mutableListOfParserRules = mutableListOf<ParserRule>()
        val mutableListOfEnumRules = mutableListOf<EnumRule>()
        val mutableListOfXtextAbstractRules = mutableListOf<XtextAbstractRule>()
        val mutableListOfXtextParserRules = mutableListOf<XtextParserRule>()
        val treeRefactorer = TreeRefactor()

        terminalRules = findAllTerminalRules()


        xtextFiles.forEach {
            val xtextParserRules = PsiTreeUtil.findChildrenOfType(it, XtextParserRule::class.java).toList()
            val xtextParserRulesRefactored = treeRefactorer.refactorXtextParserRules(xtextParserRules)
            mutableListOfXtextParserRules.addAll(xtextParserRulesRefactored)

            mutableListOfXtextAbstractRules.addAll(PsiTreeUtil.findChildrenOfType(it, XtextAbstractRule::class.java).toList())

            mutableListOfParserRules.addAll(xtextParserRulesRefactored
                    .map { ParserRule(it) })
            mutableListOfEnumRules.addAll(PsiTreeUtil.findChildrenOfType(it, XtextEnumRule::class.java)
                    .map { EnumRule(it) })
        }
        refactoredXtextParserRules = mutableListOfXtextParserRules

        xtextRuleListEditor = RuleListEditor(refactoredXtextParserRules)

        markDatatypeRules(mutableListOfParserRules)

        refactorRulesIfAssignmentCollision(mutableListOfParserRules)

        refactorRulesWithActions(mutableListOfParserRules)
        
        parserRules = mutableListOfParserRules

        enumRules = mutableListOfEnumRules

        keywordModel = XtextKeywordModel(mutableListOfXtextAbstractRules)
        referencesModel = XtextCrossReferencesModel(parserRules)
        setReferencedFieldForParcerRules()
        createVisitorGeneratorModel()

        bridgeModel = BridgeModel(createBridgeRules(), getBridgeCrossReferences())
        println("d")
    }

    private fun createBridgeRules(): List<BridgeModelRule> {
        val result = mutableListOf<BridgeModelRule>()
        parserRules.forEach { rule ->
            val literalAssignments = mutableListOf<AssignableLiteral>()
            val objectAssignments = mutableListOf<AssignableObject>()
            val rewrites = mutableListOf<Rewrite>()
            val simpleActions = mutableListOf<BridgeSimpleAction>()
            rule.alternativesElements
                    .filter { it.assignment.isNotEmpty() }
                    .forEach {
                        val assignment = createAssignmentFromString(it.assignment)
                        val bnfName = it.getBnfName()
                        val calledRule = getParserRuleByName(bnfName)
                        if (calledRule != null) {
                            if (calledRule is TerminalRule) {
                                literalAssignments.add(AssignableLiteral(assignment, calledRule.name, calledRule.returnType))
                            } else if (calledRule is ParserRule) {
                                if (calledRule.isDataTypeRule) {
                                    literalAssignments.add(AssignableLiteral(assignment, calledRule.name, calledRule.returnType))
                                } else {
                                    objectAssignments.add(AssignableObject(assignment, bnfName))

                                }
                            }

                        } else if (isKeyword(bnfName)) {
                            literalAssignments.add(AssignableKeyword(assignment, bnfName.slice(1..bnfName.length - 2)))
                        }
                    }
            rule.alternativesElements
                    .filter { it.action.isNotEmpty() }
                    .forEach {
                        val action = it.action
                        if (action.endsWith("current}")) {
                            rewrites.add(createRewriteFromText(action, it.getBnfName()))
                        } else {
                            simpleActions.add(createSimpleActionFromText(action, it.getBnfName()))
                        }
                    }
            result.add(BridgeModelRule(rule.name, rule.returnType, literalAssignments.distinct(), objectAssignments.distinct(), rewrites.distinct(), simpleActions.distinct()))
        }

        return result.toList()
    }

    private fun isKeyword(text: String): Boolean {
        return text.startsWith("\"") || text.startsWith("\'")
    }

    private fun createAssignmentFromString(string: String): Assignment {
        val assignmentPenultChar = string[string.length - 2]
        if (assignmentPenultChar == '+') {
            return Assignment(string.slice(0..string.length - 3), AssignmentType.PLUS_EQUALS)
        } else if (assignmentPenultChar == '?') {
            return Assignment(string.slice(0..string.length - 3), AssignmentType.QUESTION_EQUALS)
        } else {
            return Assignment(string.slice(0..string.length - 2), AssignmentType.EQUALS)
        }
    }

    private fun refactorRulesWithActions(rules: MutableList<ParserRule>) {
        val newRules = mutableListOf<ParserRule>()
        rules.forEach { rule ->
            rule.alternativesElements
                    .filter { it.action.isNotEmpty() }
                    .forEach {
                        if (it.refactoredName == null) {
                            val oldName = it.getBnfName()
                            val newName = createRefactoredName()
                            it.refactoredName = newName
//                            xtextRuleListEditor.changeLeavesInRule(rule.name, mapOf(Pair(PsiTreeUtil.firstChild(it.psiElement) as LeafPsiElement, it.getBnfName())))
                            xtextRuleListEditor.addRule(newName, oldName)
                            val newParserRule = createParserRule(newName, oldName)
//                            newParserRule.isDataTypeRule = isDataTypeRuleOrKeyword(oldName)
                            newParserRule.isDataTypeRule = rules.firstOrNull { it.name == oldName }?.isDataTypeRule
                                    ?: false
                            newRules.add(newParserRule)
                        }
                    }
        }
        rules.addAll(newRules)
    }


//    private fun isDataTypeRuleOrKeyword(tokenString: String): Boolean{
//        if(isKeyword(tokenString)) return true
//        else {
//            val rule = getParserRuleByName(tokenString)
//            rule?.let {
//                return it.isDataTypeRule
//            }
//        }
//        return false
//    }

    private fun getParserRuleByName(name: String): ModelRule? {
        parserRules.firstOrNull { it.name == name }?.let { return it }
        terminalRules.firstOrNull { it.name == name }?.let { return it }
        enumRules.firstOrNull { it.name == name }?.let { return it }
        return null
    }

    private fun createRefactoredName(): String {
//        var newName = ""
//        if(oldName.startsWith("\"")){
//            val stringInsideCommas = oldName.slice(1..oldName.length-2)
//            if(XtextKeywordModel.KEYWORDS.containsKey(stringInsideCommas)){
//                newName = XtextKeywordModel.KEYWORDS.get(stringInsideCommas)!!
//            }else{
//                newName = "Keyword"
//            }
//        }else{
//            if(parserRules.)
//            newName = oldName
//        }
//        newName = newName + newNamesCounter

        return "GeneratedRule${newNamesCounter++}"
    }



    private fun markDatatypeRules(rules: List<ParserRule>) {
        val terminalNames = terminalRules.stream()
                .map { it.name }
                .toList()
        val visitor = DataTypeRuleVisitor(terminalNames)
        rules.forEach {
            it.isDataTypeRule = visitor.isDataTypeRule(it.myRule)
        }
    }


    private fun getAssignment(xtextAssignment: XtextAssignment): Assignment {
        var assignmentType = AssignmentType.EQUALS
        xtextAssignment.plusEqualsKeyword?.let { assignmentType = AssignmentType.PLUS_EQUALS }
        xtextAssignment.quesEqualsKeyword?.let { assignmentType = AssignmentType.QUESTION_EQUALS }
        return Assignment(xtextAssignment.validID.text, assignmentType)

    }

    private fun refactorRulesIfAssignmentCollision(rules: MutableList<ParserRule>) {
        val changedElementsNames = mutableMapOf<String, String>()
        rules.forEach { rule ->
            val elementsToChange = findRuleElementsToRenameInParserRule(rule)
            if (elementsToChange.isNotEmpty()) {
                val mapOfLeafsToChangeInRule = mutableMapOf<LeafPsiElement, String>()
                elementsToChange.forEach {
                    val newElementName = "${it.getBnfName().capitalize()}${rule.name.capitalize()}${createAssignmentName(it.assignment).capitalize()}"
                    changedElementsNames.put(it.getBnfName(), newElementName)
                    it.refactoredName = newElementName
                    mapOfLeafsToChangeInRule.put(PsiTreeUtil.firstChild(it.psiElement) as LeafPsiElement, it.getBnfName())
                }
                xtextRuleListEditor.changeLeavesInRule(rule.name, mapOfLeafsToChangeInRule)
            }
        }
        createNewRulesAccordingToChangedElements(rules, changedElementsNames)
    }

    private fun createNewRulesAccordingToChangedElements(rules: MutableList<ParserRule>, changedElements: Map<String, String>) {
        val duplicatedRules = mutableListOf<String>()
        changedElements.forEach { originName, newName ->
            val bnfExtention = "{extends=${originName}API}"
            val originRule = rules.first { it.name == originName }
            if (!duplicatedRules.contains(originName)) {
                duplicatedRules.add(originName)
                val privateRule = createPrivateDuplicateRule(originRule)
                rules.add(privateRule)
                rules.add(createApiRule(originName))
                replaceParserRuleElementsWithText(originRule, privateRule.name)
                originRule.bnfExtentionsString = bnfExtention
                xtextRuleListEditor.duplicateRuleWithNewName(originName, "${originName}API")
            }
            xtextRuleListEditor.duplicateRuleWithNewName(originName, newName)
            val newParserRule = createParserRule(newName, "${originName}Private")
            newParserRule.bnfExtentionsString = bnfExtention
            newParserRule.isDataTypeRule = originRule.isDataTypeRule
            rulesWithSuperClass.put(originName, "${originName}API")
            rulesWithSuperClass.put(newName, "${originName}API")
            rules.add(newParserRule)
        }
    }

    private fun replaceParserRuleElementsWithText(rule: ParserRule, text: String) {
        while (rule.alternativesElements.size > 1) rule.alternativesElements.removeAt(1)
        rule.alternativesElements[0].refactoredName = text
    }

    private fun createParserRule(ruleName: String, ruleBody: String): ParserRule {
        val xtextRule = XtextElementFactory.createParserRule("$ruleName  : ${ruleBody};")
        return ParserRule(xtextRule)
    }


    private fun createApiRule(originName: String): ParserRule {
        val xtextRule = XtextElementFactory.createParserRule("${originName}API  : ${originName}Private;")
        return ParserRule(xtextRule)
    }

    private fun createPrivateDuplicateRule(rule: ParserRule): ParserRule {
        val duplicate = createRuleDuplicate("${rule.name}Private", rule)
        duplicate.isPrivate = true
        return duplicate
    }


    private fun findRuleElementsToRenameInParserRule(rule: ParserRule): List<RuleElement> {
        val result = mutableListOf<RuleElement>()
        val groupedElements = rule.alternativesElements.groupBy { it.getBnfName() }
        groupedElements.values
                .filter { it.size > 1 }
                .forEach { list ->
                    val firstAssignment = list[0].assignment
                    list.filter { it.assignment != firstAssignment }
                            .forEach {
                                result.add(it)
                            }
                }
        return result
    }


    private fun createAssignmentName(assignment: String): String {
        val assignmentPenultChar = assignment[assignment.length - 2]
        return if (assignmentPenultChar == '+' || assignmentPenultChar == '?') assignment.slice(0..assignment.length - 3)
        else assignment.slice(0..assignment.length - 2)
    }

    private fun updateRefactoredRules(ruleName: String, changedElement: RuleElement) {
        val oldRule = refactoredXtextParserRules.stream()
                .filter { it.ruleNameAndParams.validID.text == ruleName }
                .findFirst().get()
        val sb = StringBuilder()
        PsiTreeUtil.getChildrenOfType(oldRule.alternatives, LeafPsiElement::class.java)?.forEach {
            sb.append(" ")
            if (it == PsiTreeUtil.getChildOfType(changedElement.psiElement, LeafPsiElement::class.java)) sb.append(changedElement.getBnfName())
            else sb.append(it.text)
        }
        val ruleText = "${ruleName} : $sb"
        val newRule = XtextElementFactory.createParserRule(ruleText)
        refactoredXtextParserRules.remove(oldRule)

    }

    private fun findAllTerminalRules(): List<TerminalRule> {
        val mutableListOfTerminalRules = mutableListOf<TerminalRule>()
        xtextFiles.forEach {
            mutableListOfTerminalRules.addAll(PsiTreeUtil.findChildrenOfType(it, XtextTerminalRule::class.java)
                    .map { TerminalRule(it, ruleResolver) })
        }
        return mutableListOfTerminalRules
    }


    private fun createRuleDuplicate(newRuleName: String, oldRule: ParserRule): ParserRule {
        val newRule = oldRule.copy()
        newRule.name = newRuleName
        return newRule
    }


    private fun setReferencedFieldForParcerRules() {
        referencesModel.references.forEach {
            val target = it.referenceTarget.text
            parserRules.forEach {
                if (it.returnType == target) {
                    it.isReferenced = true
                }
            }
        }

    }


    private fun createVisitorGeneratorModel() {
        val terminalRulesNames = terminalRules.map { it.name }
        val crossReferencesNames = mutableListOf<String>()
        parserRules.forEach {
            findCrossReferencesInParserRule(it).forEach {
                crossReferencesNames.add(it.name)
            }
        }
        visitorGeneratorModel = VisitorGeneratorModelImpl(refactoredXtextParserRules, terminalRulesNames, rulesWithSuperClass, crossReferencesNames)

    }

    private fun createSimpleActionFromText(text: String, psiElementName: String): BridgeSimpleAction {
        var actionName = text.removePrefix("{").removeSuffix("}")
        if (actionName.contains("::")) {
            actionName = actionName.split("::")[1]
        }
        return BridgeSimpleAction(actionName, psiElementName)
    }

    private fun createRewriteFromText(text: String, psiElementName: String): Rewrite {
        var className = text.split(".")[0]
        if (className.contains("::")) {
            className = className.split("::")[1]
        }
        className = className.removePrefix("{")
        var textFragmentForAssignment = text.split(".")[1]
        textFragmentForAssignment = textFragmentForAssignment.removeSuffix("current}")
        return Rewrite(className, createAssignmentFromString(textFragmentForAssignment), psiElementName)
    }

    private fun getBridgeCrossReferences(): List<BridgeCrossReference> {
        val resultList = mutableListOf<BridgeCrossReference>()
        parserRules.forEach { rule ->
            val ruleName = rule.name
            val crossReferenceElements = findCrossReferencesInParserRule(rule)
            crossReferenceElements.forEach {
                resultList.add(createBridgeCrossReferencefromCrossReferenceElement(it, ruleName))
            }
        }
        return resultList
    }

    private fun createBridgeCrossReferencefromCrossReferenceElement(crossReferenceElement: ParserCrossReferenseElement, container: String): BridgeCrossReference {
        return BridgeCrossReference(createAssignmentFromString(crossReferenceElement.assignment), container, crossReferenceElement.referenceTarget.text, crossReferenceElement.name)
    }

    private fun findCrossReferencesInParserRule(rule: ParserRule): List<ParserCrossReferenseElement> {
        val resultList = mutableListOf<ParserCrossReferenseElement>()
        rule.alternativesElements.forEach {
            if (it is ParserCrossReferenseElement) resultList.add(it)
        }
        return resultList
    }



    class TreeRefactor {

        fun refactorXtextParserRules(oldRules: List<XtextParserRule>): List<XtextParserRule> {
            return refactorComplicatedDeligateRules(oldRules)
        }

        fun refactorComplicatedDeligateRules(oldRules: List<XtextParserRule>): List<XtextParserRule> {
            val refactoredRules = mutableListOf<XtextParserRule>()
            oldRules.forEach {
                var ruleCopy = it.copy() as XtextParserRule
                val branchesToRefactor = ComplicatedConditionalBranchesFinder.getComplicateBranchesForParserRule(it)
                for (i: Int in 0..branchesToRefactor.size - 1) {
                    val newRule = getNewRuleFromBrunch(it, branchesToRefactor[i], i + 1)
                    ruleCopy = refactorOldRule(ruleCopy, branchesToRefactor[i], newRule.ruleNameAndParams.text)
                    refactoredRules.add(newRule)
                }
                refactoredRules.add(ruleCopy)
            }
            return refactoredRules
        }



        fun refactorOldRule(oldRule: XtextParserRule, branchToReplaceWithRuleCall: XtextConditionalBranch, newRuleName: String): XtextParserRule {
            var ruleText = oldRule.text
            ruleText = ruleText.replace(branchToReplaceWithRuleCall.text, newRuleName)
            return XtextElementFactory.createParserRule(ruleText)
        }

        fun getNewRuleFromBrunch(mainRule: XtextParserRule, branch: XtextConditionalBranch, branchNumber: Int): XtextParserRule {
            val ruleName = mainRule.ruleNameAndParams.text.replace("^", "Caret").capitalize()
            val newRuleName = "${ruleName}$branchNumber"
            val newRule = XtextElementFactory.createParserRule("$newRuleName  : ${branch.text};")
            return newRule
        }

    }

    // class not finished: RuleCall to DataType rule should be taken as Terminal
    class DataTypeRuleVisitor(val terminalRuleNames: List<String>) : XtextVisitor() {
        var res = true

        fun isDataTypeRule(rule: XtextParserRule): Boolean {
            res = true
            visitParserRule(rule)
            return res
        }

        override fun visitAssignment(o: XtextAssignment) {
            res = false
        }

        override fun visitAction(o: XtextAction) {
            res = false
        }

        override fun visitRuleCall(o: XtextRuleCall) {
            if (!terminalRuleNames.contains(o.referenceAbstractRuleRuleID.text)) {
                res = false
            }
        }

        override fun visitPredicatedRuleCall(o: XtextPredicatedRuleCall) {
            if (!terminalRuleNames.contains(o.referenceAbstractRuleRuleID.text)) {
                res = false
            }
        }

        override fun visitCrossReference(o: XtextCrossReference) {
            res = false
        }


    }
}