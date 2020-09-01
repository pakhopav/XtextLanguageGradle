package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.RuleResolverImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.visitors.ComplicatedConditionalBranchesFinder
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*
import org.eclipse.emf.ecore.EPackage
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
    val importedModels: Map<String, EPackage>

    private val rulesWithSuperClass = mutableMapOf<String, String>()
    private val refactorInfoList = mutableListOf<RefactorOnAssignmentInfo>()
    private val nameGenerator = NameGenerator()
    private var newNamesCounter = 0
    private val rootRuleName: String


    init {
        var mutableListOfParserRules = mutableListOf<ParserRule>()
        val mutableListOfEnumRules = mutableListOf<EnumRule>()
        val mutableListOfXtextAbstractRules = mutableListOf<XtextAbstractRule>()
        val mutableListOfXtextParserRules = mutableListOf<XtextParserRule>()
        val treeRefactorer = TreeRefactor()

        importedModels = findImportedModels()

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
        rootRuleName = mutableListOfParserRules.first().name

        enumRules = mutableListOfEnumRules

        refactoredXtextParserRules = mutableListOfXtextParserRules

        xtextRuleListEditor = RuleListEditor(refactoredXtextParserRules)

        addParserRulesForCrossReferences(mutableListOfParserRules)

        markDatatypeRules(mutableListOfParserRules)

        refactorRulesIfAssignmentCollision(mutableListOfParserRules)

        refactorRulesWithActions(mutableListOfParserRules)
        
        parserRules = mutableListOfParserRules


        keywordModel = XtextKeywordModel(mutableListOfXtextAbstractRules)
        referencesModel = XtextCrossReferencesModel(parserRules)
        setReferencedFieldForParserRules()
        createVisitorGeneratorModel()

        bridgeModel = BridgeModel(createBridgeModelRules(), rootRuleName, getBridgeCrossReferences(), "arithmetics", "Arithmetics", refactorInfoList)
    }

    private fun createBridgeModelRules(): List<BridgeModelRule> {
        val result = mutableListOf<BridgeModelRule>()
        val duplicates = refactorInfoList.flatMap { it.duplicateRuleNames }
        val rulesDelegatedToPrivate = findRulesDelegatedToPrivate()
        parserRules
                .filter { !it.isDataTypeRule && !it.isPrivate && !it.isApiRule && !duplicates.contains(it.name) }
                .forEach { rule ->

                    val literalAssignments = mutableListOf<AssignableObject>()
                    val objectAssignments = mutableListOf<AssignableObject>()
                    val rewrites = mutableListOf<Rewrite>()
                    val simpleActions = mutableListOf<BridgeSimpleAction>()
                    var hasName = false
                    val alternatives = if (rulesDelegatedToPrivate.containsKey(rule.name)) getParserRuleByName(rulesDelegatedToPrivate[rule.name]!!)!!.alternativeElements else rule.alternativeElements
                    alternatives
                            .filter { it.assignment.isNotEmpty() && it !is ParserCrossReferenseElement }
                            .forEach { ruleElement ->
                                val assignment = createAssignmentFromString(ruleElement.assignment)
                                if (assignment.text == "name") hasName = true
                                val bnfName = ruleElement.getBnfName()
                                val calledRule = getParserRuleByName(bnfName)
                                if (calledRule != null) {
                                    if (calledRule is TerminalRule) {
                                        literalAssignments.add(AssignableObject(assignment, nameGenerator.toGKitTypesName(calledRule.name), calledRule.returnType))
                                    } else if (calledRule is ParserRule) {
                                        if (calledRule.isDataTypeRule) {
                                            literalAssignments.add(AssignableObject(assignment, nameGenerator.toGKitTypesName(calledRule.name), calledRule.returnType))
                                        } else {
                                            objectAssignments.add(AssignableObject(assignment, nameGenerator.toGKitTypesName(calledRule.name), calledRule.returnType))

                                        }
                                    }

                                } else if (isKeyword(bnfName)) {
                                    val keywordName = getKeywordName(bnfName)
                                    keywordName?.let {
                                        literalAssignments.add(AssignableKeyword(assignment, nameGenerator.toGKitTypesName(keywordName)))
                                    }
                                }
                            }
                    alternatives
                            .filter { it.action.isNotEmpty() }
                            .forEach {
                                val action = it.action
                                val returnType = getParserRuleByName(it.getBnfName())?.returnType ?: "String"
                                val psiElementType = if (isKeyword(it.getBnfName())) nameGenerator.toGKitTypesName(getKeywordName(it.getBnfName())!!) else nameGenerator.toGKitTypesName(it.getBnfName())
                                if (action.endsWith("current}")) {
                                    rewrites.add(createRewriteFromText(action, psiElementType, returnType))
                                } else {
                                    simpleActions.add(createSimpleActionFromText(action, psiElementType))
                                }
                            }
                    result.add(BridgeModelRule(rule.name, rule.returnType, literalAssignments.distinct(), objectAssignments.distinct(), rewrites.distinct(), simpleActions.distinct(), hasName))
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
            rule.alternativeElements
                    .filter { it.action.isNotEmpty() }
                    .forEach {
                        if (it.refactoredName == null && !isUniqueElementInRule(rule, it)) {
                            val oldName = it.getBnfName()
                            val newName = if (isUniqueElementInRule(rule, it)) oldName else createRefactoredName()
                            it.refactoredName = newName
//                            xtextRuleListEditor.changeLeavesInRule(rule.name, mapOf(Pair(PsiTreeUtil.firstChild(it.psiElement) as LeafPsiElement, it.getBnfName())))
                            xtextRuleListEditor.addRule(newName, oldName)
                            val originRule = getParserRuleByName(oldName, rules)
                            val newParserRule = if (originRule == null) createParserRule(newName, "", oldName) else createParserRule(newName, originRule.returnType, oldName)
                            newParserRule.isDataTypeRule = isDataTypeRuleOrKeyword(oldName, rules)

                            newRules.add(newParserRule)
                        }
                    }
        }
        rules.addAll(newRules)
    }

    private fun isUniqueElementInRule(rule: ParserRule, ruleElement: RuleElement): Boolean {
        val elementName = ruleElement.getBnfName()
        val repeatings = rule.alternativeElements.filter { it.getBnfName() == elementName }.count()
        return repeatings == 1
    }


    private fun isDataTypeRuleOrKeyword(tokenString: String, parserRules: List<ParserRule>): Boolean {
        if (isKeyword(tokenString)) return true
        else {
            val rule = getParserRuleByName(tokenString, parserRules)
            rule?.let {
                return it.isDataTypeRule
            }
        }
        return false
    }

    private fun getParserRuleByName(name: String): ModelRule? {
        parserRules.firstOrNull { it.name == name }?.let { return it }
        terminalRules.firstOrNull { it.name == name }?.let { return it }
        enumRules.firstOrNull { it.name == name }?.let { return it }
        return null
    }

    private fun getParserRuleByName(name: String, parserRules: List<ParserRule>): ModelRule? {
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
                rules.add(createApiRule(originRule))
                replaceParserRuleElementsWithText(originRule, privateRule.name)
                originRule.bnfExtensionsString = bnfExtention
                xtextRuleListEditor.duplicateRuleWithNewName(originName, "${originName}API")
                refactorInfoList.add(RefactorOnAssignmentInfo(originName, "${originName}API", privateRule.name))
            }
            xtextRuleListEditor.duplicateRuleWithNewName(originName, newName)
            val newParserRule = createParserRule(newName, originRule.returnType, "${originName}Private")
            newParserRule.bnfExtensionsString = bnfExtention
            newParserRule.isDataTypeRule = originRule.isDataTypeRule
            rulesWithSuperClass.put(originName, "${originName}API")
            rulesWithSuperClass.put(newName, "${originName}API")
            rules.add(newParserRule)
            refactorInfoList.first { it.originRuleName == originName }.duplicateRuleNames.add(newName)
        }
    }

    private fun replaceParserRuleElementsWithText(rule: ParserRule, text: String) {
        while (rule.alternativeElements.size > 1) rule.alternativeElements.removeAt(1)
        rule.alternativeElements[0].refactoredName = text
    }

    private fun createParserRule(ruleName: String, extension: String, ruleBody: String): ParserRule {
        val xtextRule = XtextElementFactory.createParserRule("$ruleName ${if (extension.isNotEmpty()) "returns $extension" else ""} : ${ruleBody};")
        return ParserRule(xtextRule)
    }


    private fun createApiRule(originRule: ParserRule): ParserRule {
        val resultRule = createParserRule("${originRule.name}API", originRule.returnType, "${originRule.name}Private")
        resultRule.isApiRule = true
        return resultRule
    }

    private fun createPrivateDuplicateRule(rule: ParserRule): ParserRule {
        val duplicate = createRuleDuplicate("${rule.name}Private", rule)
        duplicate.isPrivate = true
        return duplicate
    }


    private fun findRuleElementsToRenameInParserRule(rule: ParserRule): List<RuleElement> {
        val result = mutableListOf<RuleElement>()
        val groupedElements = rule.alternativeElements.groupBy { it.getBnfName() }

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
        newRule.returnType = oldRule.returnType
        return newRule
    }


    private fun setReferencedFieldForParserRules() {
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

    private fun createRewriteFromText(text: String, psiElementType: String, returnType: String): Rewrite {
        var className = text.split(".")[0]
        if (className.contains("::")) {
            className = className.split("::")[1]
        }
        className = className.removePrefix("{")
        var textFragmentForAssignment = text.split(".")[1]
        textFragmentForAssignment = textFragmentForAssignment.removeSuffix("current}")
        return Rewrite(className, createAssignmentFromString(textFragmentForAssignment), psiElementType, returnType)
    }

    private fun getBridgeCrossReferences(): List<BridgeCrossReference> {
        val resultList = mutableListOf<BridgeCrossReference>()
        parserRules.forEach { rule ->
            val ruleName = rule.name
            val crossReferenceElements = findCrossReferencesInParserRule(rule)
            crossReferenceElements.forEach {
                resultList.add(createBridgeCrossReferencefromCrossReferenceElement(it, rule.returnType))
            }
        }
        return resultList
    }

    private fun createBridgeCrossReferencefromCrossReferenceElement(crossReferenceElement: ParserCrossReferenseElement, container: String): BridgeCrossReference {
        return BridgeCrossReference(createAssignmentFromString(crossReferenceElement.assignment), container, crossReferenceElement.referenceTarget.text, crossReferenceElement.name.replace("_", ""))
    }

    private fun findCrossReferencesInParserRule(rule: ParserRule): List<ParserCrossReferenseElement> {
        val resultList = mutableListOf<ParserCrossReferenseElement>()
        rule.alternativeElements.forEach {
            if (it is ParserCrossReferenseElement) resultList.add(it)
        }
        return resultList
    }


    private fun addParserRulesForCrossReferences(rules: MutableList<ParserRule>) {
        val newRules = mutableListOf<ParserRule>()
        rules.forEach { rule ->
            val crossRefs = rule.alternativeElements.filter { it is ParserCrossReferenseElement }.map { it as ParserCrossReferenseElement }.toList()
            crossRefs.forEach { reference ->
                val newRule = createParserRule(reference.getBnfName(), "", reference.referenceType)
                newRule.returnType = "String"
                newRule.isDataTypeRule = true
                newRules.add(newRule)
            }

        }
        rules.addAll(newRules)
    }

    private fun findRulesDelegatedToPrivate(): Map<String, String> {
        val resultMap = mutableMapOf<String, String>()
        refactorInfoList.forEach {
            resultMap.put(it.originRuleName, it.privateRuleName)
        }
        return resultMap
    }

    private fun getKeywordName(text: String): String? {
        val keywordText = text.replace("\"", "").replace("\'", "")
        return keywordModel.keywords.firstOrNull { it.keyword.slice(1..it.keyword.length - 2) == keywordText }?.name
    }

    private fun findImportedModels(): Map<String, EPackage> {
        val resultMap = mutableMapOf<String, EPackage>()

        return resultMap
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
            val newRuleReturnType = mainRule.typeRef?.referenceEcoreEClassifier?.text ?: ruleName

            val newRule = XtextElementFactory.createParserRule("$newRuleName returns $newRuleReturnType : ${branch.text};")
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