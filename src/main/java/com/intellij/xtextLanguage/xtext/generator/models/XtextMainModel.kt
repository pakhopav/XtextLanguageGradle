package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.EcorePackageRegistry
import com.intellij.xtextLanguage.xtext.generator.RuleResolverImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.visitors.ComplicatedConditionalBranchesFinder
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*
import org.eclipse.emf.ecore.EPackage
import java.util.*

class XtextMainModel(val xtextFiles: List<XtextFile>) {
    val parserRules: List<ParserRule>
    val enumRules: List<EnumRule>
    val terminalRules: MutableList<TerminalRule>
    val keywordModel: XtextKeywordModel
    val ruleResolver = RuleResolverImpl(this)
    val bridgeModel: BridgeModel
    val crossReferences: List<ParserCrossReferenceElement>

    private val refactoredXtextParserRules: MutableList<XtextParserRule>
    private val importedModels: Map<String, EPackage>
    private val rulesWithSuperClass = mutableMapOf<String, String>()
    private val refactorInfoList = mutableListOf<RefactorOnAssignmentInfo>()
    private val nameGenerator = NameGenerator()
    private var newNamesCounter = 0
    private val rootRuleName: String
    private val registry = EcorePackageRegistry.instance


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
        crossReferences = findAllCrossReferences(mutableListOfParserRules)

        rootRuleName = mutableListOfParserRules.first().name

        enumRules = mutableListOfEnumRules

        refactoredXtextParserRules = mutableListOfXtextParserRules

        addExtensionsToRules(mutableListOfParserRules)

        addParserRulesForCrossReferences(mutableListOfParserRules)

        markDatatypeRules(mutableListOfParserRules)

        refactorRulesIfAssignmentCollision(mutableListOfParserRules)

        refactorRulesWithActions(mutableListOfParserRules)
        
        parserRules = mutableListOfParserRules

        keywordModel = XtextKeywordModel(mutableListOfXtextAbstractRules)

        setReferencedFieldForParserRules()

        bridgeModel = BridgeModel(createBridgeModelRules(), rootRuleName, getBridgeCrossReferences(), refactorInfoList)
        print("")
    }


    private fun findAllCrossReferences(rules: List<ParserRule>): List<ParserCrossReferenceElement> {
        val resultList = rules
                .flatMap { it.alternativeElements }
                .filterIsInstance<ParserCrossReferenceElement>()
                .toList()
        resultList.forEach { reference ->
            val targets = rules
                    .filter {
                        val returnType = if (it.returnTypeText.isNotEmpty()) it.returnTypeText else it.name
                        returnType == reference.referenceTarget
                    }
                    .map { CrossReferenceTarget(it.name) }
            reference.targets.addAll(targets)
        }
        return resultList
    }


    private fun addExtensionsToRules(rules: List<ParserRule>) {
        crossReferences.flatMap { it.targets }.forEach { target ->
            val targetRule = getRuleByName(target.superRuleName, rules) as ParserRule
            setSupertypeToDelegatedRules(targetRule, target, rules)
        }
    }

    private fun setSupertypeToDelegatedRules(rule: ParserRule, target: CrossReferenceTarget, allRules: List<ParserRule>) {
        val ruleCallElementsWithoutAssignment = rule.alternativeElements.filter { it is ParserRuleCallElement && it.assignment.isEmpty() }
        ruleCallElementsWithoutAssignment.forEach {
            val calledRule = getRuleByName(it.getBnfName(), allRules)
            if (calledRule is ParserRule) {
                setSupertypeToDelegatedRules(calledRule, target, allRules)
                if (hasName(calledRule)) {
                    calledRule.addStringToBnfExtension("extends=${target.superRuleName}")
                    target.subRuleNames.add(calledRule.name)
                }
            }

        }
    }

    private fun hasName(rule: ParserRule): Boolean {
        return rule.alternativeElements.any { it.assignment == "name=" }
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
                    val alternatives = if (rulesDelegatedToPrivate.containsKey(rule.name)) getRuleByName(rulesDelegatedToPrivate[rule.name]!!)!!.alternativeElements else rule.alternativeElements
                    alternatives
                            .filter { it.assignment.isNotEmpty() && it !is ParserCrossReferenceElement }
                            .forEach { ruleElement ->
                                val assignment = createAssignmentFromString(ruleElement.assignment)
                                if (assignment.text == "name") hasName = true
                                val bnfName = ruleElement.getBnfName()
                                val calledRule = getRuleByName(bnfName)
                                if (calledRule != null) {
                                    if (calledRule is TerminalRule) {
                                        literalAssignments.add(AssignableObject(assignment, nameGenerator.toGKitTypesName(calledRule.name), getRuleReturnType(calledRule)))
                                    } else if (calledRule is ParserRule) {
                                        if (calledRule.isDataTypeRule) {
                                            literalAssignments.add(AssignableObject(assignment, nameGenerator.toGKitTypesName(calledRule.name), getRuleReturnType(calledRule)))
                                        } else {
                                            objectAssignments.add(AssignableObject(assignment, nameGenerator.toGKitTypesName(calledRule.name), getRuleReturnType(calledRule)))

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
                                val calledRule = getRuleByName(it.getBnfName())
                                val returnType = if (calledRule != null) getRuleReturnType(calledRule) else BridgeRuleType("String", "")
                                val psiElementType = if (isKeyword(it.getBnfName())) nameGenerator.toGKitTypesName(getKeywordName(it.getBnfName())!!) else nameGenerator.toGKitTypesName(it.getBnfName())
                                if (action.endsWith("current}")) {
                                    rewrites.add(createRewriteFromText(action, psiElementType, returnType))
                                } else {
                                    simpleActions.add(createSimpleActionFromText(action, psiElementType))
                                }
                            }
                    result.add(BridgeModelRule(rule.name, getRuleReturnType(rule), literalAssignments.distinct(), objectAssignments.distinct(), rewrites.distinct(), simpleActions.distinct(), hasName))
        }

        return result.toList()
    }

//    private fun findReturnType(returnTypeText: String): String {
//
//        if (returnTypeText.contains("::")) {
//            val modelName = returnTypeText.split("::")[0]
//            val modelType = returnTypeText.split("::")[1]
//            val modelPackage = importedModels.get(modelName)
//            return modelPackage!!.getEClassifier(modelType).instanceTypeName
//        } else {
//            importedModels.filter { it.key.isEmpty() }.values.forEach { ePackage ->
//                val result = ePackage.getEClassifier(returnTypeText)
//                if (result != null) return result.instanceTypeName
//            }
//
//        }
//        return returnTypeText
//    }

    private fun getRuleReturnType(rule: ModelRule): BridgeRuleType {
        if (rule.returnTypeText.isEmpty()) {
            if (rule.isDataTypeRule) {
                return BridgeRuleType("String", "")
            } else {
                return createBridgeRuleTypeFromTypeName(rule.name)
            }
        } else {
            return createBridgeRuleTypeFromTypeName(rule.returnTypeText)
        }
    }

    private fun createBridgeRuleTypeFromTypeName(typeName: String): BridgeRuleType {

        if (typeName.contains("::")) {
            val modelName = typeName.split("::")[0]
            val modelType = typeName.split("::")[1]
            val modelPackage = importedModels.get(modelName)
            return BridgeRuleType(modelPackage!!.getEClassifier(modelType).instanceTypeName, modelPackage.nsPrefix)
        } else {
            importedModels.filter { it.key.isEmpty() }.values.forEach { ePackage ->
                val result = ePackage.getEClassifier(typeName)
                if (result != null) {
                    return BridgeRuleType(result.instanceTypeName, ePackage.nsPrefix)
                }
            }

        }
        return BridgeRuleType(typeName, "")
    }

    private fun getModelPackage(returnTypeText: String): EPackage? {
        if (returnTypeText.contains("::")) {
            val modelName = returnTypeText.split("::")[0]
            return importedModels.get(modelName)
        } else {
            importedModels.filter { it.key.isEmpty() }.values.forEach { ePackage ->
                val result = ePackage.getEClassifier(returnTypeText)
                if (result != null) return ePackage
            }
        }
        return null
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
                            val originRule = getRuleByName(oldName, rules)
                            val newParserRule = if (originRule == null) createParserRuleWithExtension(newName, "", oldName) else createParserRuleWithExtension(newName, originRule.returnTypeText, oldName)
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
            val rule = getRuleByName(tokenString, parserRules)
            rule?.let {
                return it.isDataTypeRule
            }
        }
        return false
    }

    private fun getRuleByName(name: String): ModelRule? {
        return getRuleByName(name, parserRules)
    }

    private fun getRuleByName(name: String, parserRules: List<ParserRule>): ModelRule? {
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
        val dataTypeNames = mutableListOf<String>()
        dataTypeNames.addAll(terminalRules.map { it.name })
        var namesListChanged = true
        val rulesToCheck = LinkedList<ParserRule>()
        val parserRules = mutableListOf<ParserRule>()
        parserRules.addAll(rules)
        while (namesListChanged) {
            namesListChanged = false
            rulesToCheck.addAll(parserRules)
            parserRules.clear()
            while (rulesToCheck.isNotEmpty()) {
                val nextRule = rulesToCheck.poll()
                if (isDatatypeRule(nextRule, dataTypeNames)) {
                    nextRule.isDataTypeRule = true
                    dataTypeNames.add(nextRule.name)
                    namesListChanged = true
                } else {
                    parserRules.add(nextRule)
                }
            }
        }
    }

    private fun isDatatypeRule(rule: ParserRule, datatypeNames: List<String>): Boolean {
        var res = true
        rule.alternativeElements.forEach {
            if ((it is ParserRuleCallElement && !datatypeNames.contains(it.getBnfName())) || it.assignment.isNotEmpty() || it.action.isNotEmpty()) {
                res = false
            }
        }
        return res
    }


    private fun refactorRulesIfAssignmentCollision(rules: MutableList<ParserRule>) {
        val changedElementNames = mutableListOf<Pair<String, String>>()
        rules.forEach { rule ->
            val elementsToChange = findRuleElementsToRenameInParserRule(rule)
            if (elementsToChange.isNotEmpty()) {
                val leafsToChangeInRule = mutableMapOf<LeafPsiElement, String>()
                elementsToChange.forEach {
                    var newElementName = "${it.getBnfName().capitalize()}${rule.name.capitalize()}${createAssignmentName(it.assignment).capitalize()}"
                    if (it is ParserRuleCallElement && getRuleByName(it.getBnfName(), rules) is TerminalRule) {
                        newElementName = nameGenerator.toGKitTypesName(newElementName)
                    }
                    changedElementNames.add(Pair(it.getBnfName(), newElementName))
                    it.refactoredName = newElementName
                    leafsToChangeInRule.put(PsiTreeUtil.firstChild(it.psiElement) as LeafPsiElement, it.getBnfName())
                }
            }
        }
        createNewRulesAccordingToChangedElements(rules, changedElementNames)
    }


    private fun createNewRulesAccordingToChangedElements(rules: MutableList<ParserRule>, changedElements: List<Pair<String, String>>) {
        val duplicatedRules = mutableListOf<String>()
        changedElements.forEach { (originName, newName) ->
            val bnfExtension = "extends=$originName"
            val originRule = getRuleByName(originName, rules)
            if (originRule is ParserRule) {
                if (!duplicatedRules.contains(originName)) {
                    duplicatedRules.add(originName)
                    val privateRule = createPrivateDuplicateRuleToOrigin(originRule)
                    rules.add(privateRule)
                    replaceParserRuleBodyWithText(originRule, privateRule.name)
                    refactorInfoList.add(RefactorOnAssignmentInfo(originName, privateRule.name))
                }
                val newParserRule = createParserRuleWithExtension(newName, originRule.returnTypeText, "${originName}Private")
                newParserRule.addStringToBnfExtension(bnfExtension)
                newParserRule.isDataTypeRule = originRule.isDataTypeRule
                rulesWithSuperClass.put(newName, originName)
                rules.add(newParserRule)
                refactorInfoList.first { it.originRuleName == originName }.duplicateRuleNames.add(newName)

            } else if (originRule is TerminalRule) {
                val newRule = createParserRuleWithExtension(newName, "", originName)
                newRule.isDataTypeRule = true
                rules.add(newRule)
            }
        }
    }

    private fun replaceParserRuleBodyWithText(rule: ParserRule, text: String) {
        val stubElement = ParserSimpleElement(XtextElementFactory.createValidID(text))
        rule.alternativeElements.clear()
        rule.alternativeElements.add(stubElement)
    }

    private fun createParserRuleWithExtension(ruleName: String, extension: String, ruleBody: String): ParserRule {
        val xtextRule = XtextElementFactory.createParserRule("$ruleName ${if (extension.isNotEmpty()) "returns $extension" else ""} : ${ruleBody};")
        return ParserRule(xtextRule)
    }


    private fun createApiRuleToOrigin(originRule: ParserRule): ParserRule {
        val resultRule = createParserRuleWithExtension("${originRule.name}API", originRule.returnTypeText, "${originRule.name}Private")
        resultRule.isApiRule = true
        return resultRule
    }

    private fun createPrivateDuplicateRuleToOrigin(originRule: ParserRule): ParserRule {
        val duplicate = createRuleDuplicate("${originRule.name}Private", originRule)
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

//    private fun updateRefactoredRules(ruleName: String, changedElement: RuleElement) {
//        val oldRule = refactoredXtextParserRules.stream()
//                .filter { it.ruleNameAndParams.validID.text == ruleName }
//                .findFirst().get()
//        val sb = StringBuilder()
//        PsiTreeUtil.getChildrenOfType(oldRule.alternatives, LeafPsiElement::class.java)?.forEach {
//            sb.append(" ")
//            if (it == PsiTreeUtil.getChildOfType(changedElement.psiElement, LeafPsiElement::class.java)) sb.append(changedElement.getBnfName())
//            else sb.append(it.text)
//        }
//        val ruleText = "${ruleName} : $sb"
//        val newRule = XtextElementFactory.createParserRule(ruleText)
//        refactoredXtextParserRules.remove(oldRule)
//
//    }

    private fun findAllTerminalRules(): MutableList<TerminalRule> {
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


    private fun setReferencedFieldForParserRules() {
        crossReferences.forEach {
            val target = it.referenceTarget
            parserRules.forEach {
                val returnType = if (it.returnTypeText.isNotEmpty()) it.returnTypeText else it.name
                if (returnType == target) {
                    it.isReferenced = true

                }
            }
        }

    }


//    private fun createVisitorGeneratorModel() {
//        val terminalRulesNames = terminalRules.map { it.name }
//        val crossReferencesNames = mutableListOf<String>()
//        parserRules.forEach {
//            findCrossReferencesInParserRule(it).forEach {
//                crossReferencesNames.add(it.name)
//            }
//        }
//        visitorGeneratorModel = VisitorGeneratorModelImpl(refactoredXtextParserRules, terminalRulesNames, rulesWithSuperClass, crossReferencesNames)
//
//    }

    private fun createSimpleActionFromText(text: String, psiElementName: String): BridgeSimpleAction {
        var actionName = text.removePrefix("{").removeSuffix("}")
        return BridgeSimpleAction(createBridgeRuleTypeFromTypeName(actionName), psiElementName)
    }

    private fun createRewriteFromText(text: String, psiElementType: String, returnType: BridgeRuleType): Rewrite {
        var className = text.split(".")[0]
        className = className.removePrefix("{")
        var textFragmentForAssignment = text.split(".")[1]
        textFragmentForAssignment = textFragmentForAssignment.removeSuffix("current}")
        return Rewrite(createBridgeRuleTypeFromTypeName(className), createAssignmentFromString(textFragmentForAssignment), psiElementType, returnType)
    }

    private fun getBridgeCrossReferences(): List<BridgeCrossReference> {
        val resultList = mutableListOf<BridgeCrossReference>()
        parserRules.forEach { rule ->
            val crossReferenceElements = findCrossReferencesInParserRule(rule)
            crossReferenceElements.forEach {
                resultList.add(createBridgeCrossReferenceFromCrossReferenceElement(it, getRuleReturnType(rule)))
            }
        }
        return resultList
    }

    private fun createBridgeCrossReferenceFromCrossReferenceElement(crossReferenceElement: ParserCrossReferenceElement, containerType: BridgeRuleType): BridgeCrossReference {
        return BridgeCrossReference(createAssignmentFromString(crossReferenceElement.assignment), containerType, createBridgeRuleTypeFromTypeName(crossReferenceElement.referenceTarget), crossReferenceElement.name.replace("_", ""))
    }

    private fun findCrossReferencesInParserRule(rule: ParserRule): List<ParserCrossReferenceElement> {
        val resultList = mutableListOf<ParserCrossReferenceElement>()
        rule.alternativeElements.forEach {
            if (it is ParserCrossReferenceElement) resultList.add(it)
        }
        return resultList
    }


    private fun addParserRulesForCrossReferences(rules: MutableList<ParserRule>) {
        val newRules = mutableListOf<ParserRule>()
        crossReferences.distinctBy { it.name }.forEach { reference ->
            val newRule = createParserRuleWithExtension(reference.getBnfName(), "", reference.referenceType)
            newRule.returnTypeText = "String"
            newRule.isDataTypeRule = true
            newRules.add(newRule)
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
        return keywordModel.keywords.firstOrNull { it.keyword == keywordText }?.name
    }

    private fun findImportedModels(): Map<String, EPackage> {
        val resultMap = mutableMapOf<String, EPackage>()
        val referencedMetamodels = xtextFiles.flatMap { PsiTreeUtil.findChildrenOfType(it, XtextReferencedMetamodel::class.java) }
        referencedMetamodels.forEach { model ->
            val modelUri = model.referenceEcoreEPackageSTRING.text.replace("\"", "").replace("\'", "")
            registry.getPackage(modelUri)?.let {
                resultMap.put(model.validID?.text ?: "", it)
            }
        }
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
            val newRuleReturnType = mainRule.typeRef?.text ?: ruleName

            val newRule = XtextElementFactory.createParserRule("$newRuleName returns $newRuleReturnType : ${branch.text};")
            return newRule
        }

    }
//
//    private fun isDataTypeRule(rule: ParserRule): Boolean{
//        val terminalRuleNames = terminalRules.map { it.name }
//        var res = true
//        rule.alternativeElements.forEach {
//            if(it is ParserRuleCallElement && !terminalRuleNames.contains(it.getBnfName())){
//                res = false
//            }else if ( it.assignment.isNotEmpty() || it.action.isNotEmpty()) {
//                res = false
//            }
//        }
//        return res
//    }

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