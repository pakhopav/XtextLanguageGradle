package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.RuleResolverImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.psi.*
import java.util.*

class XtextMainModel(val xtextFiles: List<XtextFile>) {
    val parserRules: List<ParserRule>
    val enumRules: List<EnumRule>
    val terminalRules: MutableList<TerminalRule>
    val keywordModel: XtextKeywordModel
    val ruleResolver = RuleResolverImpl(this)
    val bridgeModel: BridgeModel
    val crossReferences: List<ParserCrossReferenceElement>

    val emfRegistry = BridgeRuleTypeRegistry.forXtextFiles(xtextFiles)

    private val rulesWithSuperClass = mutableMapOf<String, String>()
    private val refactorInfoList = mutableListOf<RefactorOnAssignmentInfo>()
    private val nameGenerator = NameGenerator()
    private var newNamesCounter = 0
    private val rootRuleName: String

    private val ruleNameToRefactoringsNumber = mutableMapOf<String, Int>()
    private val parserRulesWithFragmentsInlined: MutableList<ParserRule>
    private val fragmentRules: MutableList<ModelRule>
    private val parserRuleCreator = ParserRuleCreator()


    init {
        val parserRulesList = mutableListOf<ParserRule>()
        val mutableListOfEnumRules = mutableListOf<EnumRule>()
        val mutableListOfXtextAbstractRules = mutableListOf<XtextAbstractRule>()

        terminalRules = findAllTerminalRules()

        xtextFiles.forEach {

            val xtextParserRules = PsiTreeUtil.findChildrenOfType(it, XtextParserRule::class.java).toList()

            val xtextParserRulesRefactored = refactorComplicatedDeligateRules(xtextParserRules)

            mutableListOfXtextAbstractRules.addAll(PsiTreeUtil.findChildrenOfType(it, XtextAbstractRule::class.java).toList())

            xtextParserRulesRefactored.forEach {
                parserRuleCreator.createRuleSimple(it)?.let { parserRulesList.add(it) }
            }

//            xtextParserRulesRefactored.forEach {
//                val creationResult = parserRuleCreator.createRule(it)
//                creationResult?.let {
//                    parserRulesList.add(creationResult.getRule())
//                    parserRulesList.addAll(creationResult.getSuffixList())
//                }
//            }

            mutableListOfEnumRules.addAll(PsiTreeUtil.findChildrenOfType(it, XtextEnumRule::class.java)
                    .map { EnumRule(it) })
        }
        fff(parserRulesList)
        fragmentRules = parserRulesList.filter { it.isPrivate }.toMutableList()

        addParserRulesForCrossReferences(parserRulesList)


        keywordModel = XtextKeywordModel(mutableListOfXtextAbstractRules)
        rootRuleName = parserRulesList.first().name
        enumRules = mutableListOfEnumRules

        parserRulesWithFragmentsInlined = createRuleCopyListWithInlinedFragments(parserRulesList)

        crossReferences = getAllCrossReferences(parserRulesList)
        setTargetsForCrossReferences()
        fixInheritanceOfNamedRules(parserRulesList)
        setIsRefencedField(parserRulesList)

        markDatatypeRules(parserRulesList)

        refactorParserRules(parserRulesList)
        parserRules = parserRulesList

        markReferencedDelegateOnlyRules()

        bridgeModel = BridgeModel(createBridgeModelRules(), rootRuleName, getBridgeCrossReferences(), refactorInfoList)

        print("")
    }


    private fun fff(rules: List<ParserRule>) {
        rules.forEach {
            val elements = it.getRuleElementsList()
            val elements2 = it.alternativeElements.filter { it !is BnfServiceElement }
            print("")
        }

    }


    private fun setTargetsForCrossReferences() {
        crossReferences.forEach { reference ->
            val targets = parserRulesWithFragmentsInlined
                    .filter {
                        val returnType = if (it.returnTypeText.isNotEmpty()) it.returnTypeText else it.name
                        returnType == reference.referenceTargetText
                    }
                    .map {
                        CrossReferenceTarget(it.name, getNameElements(it).map { nameGenerator.toGKitClassName(it) }.distinct())
                    }
            reference.targets.addAll(targets)
        }
    }

    private fun fillRuleNameToRefactoringsNumberMap(parserRules: List<ParserRule>) {
        parserRules.forEach { ruleNameToRefactoringsNumber.putIfAbsent(it.name, 0) }
        terminalRules.forEach { ruleNameToRefactoringsNumber.putIfAbsent(it.name, 0) }
        enumRules.forEach { ruleNameToRefactoringsNumber.putIfAbsent(it.name, 0) }
    }

    private fun refactorParserRules(rules: MutableList<ParserRule>) {

        fillRuleNameToRefactoringsNumberMap(rules)

        refactorRulesIfAssignmentCollision(parserRulesWithFragmentsInlined, rules)

        refactorRulesWithActions(parserRulesWithFragmentsInlined, rules)
    }

    private fun createRuleCopyListWithInlinedFragments(rules: List<ParserRule>): MutableList<ParserRule> {
        val resultList = mutableListOf<ParserRule>()
        val fragmentRuleNames = fragmentRules.map { it.name }
        var fragmentInlined = true
        resultList.addAll(rules.filter { !it.isPrivate }.map { it.copy() })
        while (fragmentInlined) {
            fragmentInlined = false
            resultList.forEach { rule ->
//                val ruleCopy =  rule.copy()
                val ruleCopyAlternatives = rule.alternativeElements
                val ruleCallsToFragments = ruleCopyAlternatives
                        .filterIsInstance<ParserRuleCallElement>()
                        .filter { fragmentRuleNames.contains(it.getBnfName()) }
                ruleCallsToFragments.forEach { ruleCall ->
                    val fragmentRule = rules.firstOrNull { it.name == ruleCall.getBnfName() }
                    val ruleCallIndex = ruleCopyAlternatives.indexOf(ruleCall)
                    ruleCopyAlternatives.removeAt(ruleCallIndex)
                    ruleCopyAlternatives.addAll(ruleCallIndex, fragmentRule!!.alternativeElements)
                    fragmentInlined = true
                }
            }
        }
        return resultList

    }

    private fun markReferencedDelegateOnlyRules() {
        val referensedRuleNames = crossReferences.flatMap { it.targets }.map { it.superRuleName }
        parserRules.filter { referensedRuleNames.contains(it.name) }.forEach {
            if (isDelegateOnlyRule(it)) it.isReferencedDelegateRule = true
        }
    }

    private fun isDelegateOnlyRule(rule: ParserRule): Boolean {
        rule.alternativeElements.filter { it !is BnfServiceElement }.forEach {
            if (it is ParserRuleCallElement && it.assignment.isEmpty()) {
                val calledRule = getRuleByName(it.getBnfName())
                if (calledRule is ParserRule) {
                    if (calledRule.isPrivate) return false
                }
            } else {
                return false
            }
        }
        return true
    }

    private fun getAllCrossReferences(rules: List<ParserRule>): List<ParserCrossReferenceElement> {
        val resultList = rules
                .flatMap { it.alternativeElements }
                .filterIsInstance<ParserCrossReferenceElement>()
                .toList()





        return resultList
    }


    private fun fixInheritanceOfNamedRules(rules: List<ParserRule>) {
        crossReferences.distinctBy { it.name }.flatMap { it.targets }.forEach { target ->
            val targetRule = getRuleByName(target.superRuleName, parserRulesWithFragmentsInlined) as ParserRule
            setSupertypeToDelegatedRules(targetRule, target, rules)
        }
    }

    private fun setIsRefencedField(rules: List<ParserRule>) {

        val rulesWithReferencedSupertype = crossReferences
                .flatMap { it.targets }
                .flatMap { it.subRules }
                .map { it.name }
                .distinct()

        crossReferences.distinctBy { it.name }
                .flatMap { it.targets }
                .map { it.superRuleName }
                .forEach { targetRuleName ->
                    if (!rulesWithReferencedSupertype.contains(targetRuleName)) {
                        val targetRule = getRuleByName(targetRuleName, rules) as ParserRule
                        targetRule.isReferenced = true
                    }
                }
    }


//    private fun setSupertypeToDelegatedRules(rule: ParserRule, target: CrossReferenceTarget, realRules: List<ParserRule>, lastNotPrivateRule: ParserRule? = null ) {
//        val ruleCallElementsWithoutAssignment = rule.alternativeElements.filter { it is ParserRuleCallElement && it.assignment.isEmpty() }.distinctBy { it.getBnfName() }
//        ruleCallElementsWithoutAssignment.forEach {
//            val calledRule = getRuleByName(it.getBnfName(), parserRulesWithFragmentsInlined)
//            if (calledRule is ParserRule) {
//                var lastNotPrivate : ParserRule? = lastNotPrivateRule
//                if(!calledRule.isPrivate) lastNotPrivate = calledRule
//                setSupertypeToDelegatedRules(calledRule, target, realRules, lastNotPrivate)
//
//                if (hasName(calledRule)) {
//                    if(!calledRule.isPrivate){
//                        calledRule.bnfExtensionsStrings.add("extends=${target.superRuleName}")
//
//                        target.subRuleNames.add(calledRule.name)
//                    }else{
//                        lastNotPrivateRule?.let {
//                            it.bnfExtensionsStrings.add("extends=${target.superRuleName}")
//                            target.subRuleNames.add(it.name)
//                        }
//
//                    }
//                }
//            }
//
//        }
//    }

    private fun setSupertypeToDelegatedRules(rule: ParserRule, target: CrossReferenceTarget, realRules: List<ParserRule>) {
        val ruleCallElementsWithoutAssignment = rule.alternativeElements.filter { it is ParserRuleCallElement && it.assignment.isEmpty() }.distinctBy { it.getBnfName() }
        ruleCallElementsWithoutAssignment.forEach {
            val calledRule = getRuleByName(it.getBnfName(), parserRulesWithFragmentsInlined)
            if (calledRule is ParserRule) {
                setSupertypeToDelegatedRules(calledRule, target, realRules)
                val ruleNameElements = getNameElements(calledRule).distinct()
                if (ruleNameElements.isNotEmpty()) {
                    realRules.firstOrNull { it.name == calledRule.name }?.bnfExtensionsStrings?.add("extends=${target.superRuleName}")
                    val subRule = TargetSubRule(calledRule.name, ruleNameElements.map { nameGenerator.toGKitClassName(it) })
                    target.subRules.add(subRule)
                }
            }

        }
    }

    private fun getNameElements(rule: ParserRule): List<String> {
        return rule.alternativeElements.filter { it.assignment == "name=" }.map { it.getBnfName() }.toList()
    }


    private fun createBridgeModelRules(): List<BridgeModelRule> {
        val result = mutableListOf<BridgeModelRule>()
        val duplicates = refactorInfoList.flatMap { it.duplicateRuleNames }
        val referensedRules = crossReferences.flatMap { it.targets }.map { it.superRuleName }
//        val rulesDelegatedToPrivate = findRulesDelegatedToPrivate()
        parserRules
                .filter { !it.isDataTypeRule && !it.isPrivate && !duplicates.contains(it.name) && !it.isReferencedDelegateRule }
                .forEach { rule ->

                    val literalAssignments = mutableListOf<AssignableObject>()
                    val objectAssignments = mutableListOf<AssignableObject>()
                    val rewrites = mutableListOf<Rewrite>()
                    val simpleActions = mutableListOf<BridgeSimpleAction>()
                    var hasName = false
                    val alternatives = if (hasFragment(rule)) parserRulesWithFragmentsInlined.first { it.name == rule.name }!!.alternativeElements else rule.alternativeElements
                    alternatives
                            .filter { it.assignment.isNotEmpty() && it !is ParserCrossReferenceElement }
                            .forEach { ruleElement ->
                                val assignment = Assignment.fromString(ruleElement.assignment)
                                val returnTypes = mutableListOf<String>()
                                if (assignment.text == "name") hasName = true
                                val bnfName = ruleElement.getBnfName()
                                val calledRule = getRuleByName(bnfName)
                                if (calledRule != null) {
                                    returnTypes.add(nameGenerator.toGKitTypesName(calledRule.name))
                                    if (referensedRules.contains(calledRule.name)) {
                                        val delegatedRuleNames = findDelegatedRuleNames(calledRule)
                                        returnTypes.addAll(delegatedRuleNames.map { nameGenerator.toGKitTypesName(it) })
                                    }
                                    if (calledRule is TerminalRule) {
                                        literalAssignments.add(AssignableObject(assignment, returnTypes, getRuleReturnType(calledRule)))
                                    } else if (calledRule is ParserRule) {
                                        if (calledRule.isDataTypeRule) {
                                            literalAssignments.add(AssignableObject(assignment, returnTypes, getRuleReturnType(calledRule)))
                                        } else {
                                            objectAssignments.add(AssignableObject(assignment, returnTypes, getRuleReturnType(calledRule)))

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
//                                val calledRule = getRuleByName(it.getBnfName())
//                                val returnType = if (calledRule != null) getRuleReturnType(calledRule) else BridgeRuleType("String", "")
                                val returnType = getRuleReturnType(rule)
                                val psiElementType = if (isKeyword(it.getBnfName())) nameGenerator.toGKitTypesName(getKeywordName(it.getBnfName())!!) else nameGenerator.toGKitTypesName(it.getBnfName())
                                if (action.endsWith("current}")) {
                                    rewrites.add(createRewriteFromText(action, psiElementType, returnType))
                                } else {
                                    simpleActions.add(createSimpleActionFromText(action, psiElementType))
                                }
                            }
                    result.add(BridgeModelRule(rule, getRuleReturnType(rule), literalAssignments.distinct(), objectAssignments.distinct(), rewrites.distinct(), simpleActions.distinct(), hasName))
                }

        return result.toList()
    }

    private fun hasFragment(rule: ParserRule): Boolean {
        val fragmentRuleNames = fragmentRules.map { it.name }
        return rule.alternativeElements.any { it is ParserRuleCallElement && fragmentRuleNames.contains(it.getBnfName()) }
    }

    private fun findDelegatedRuleNames(rule: ModelRule): List<String> {
        val resultList = mutableListOf<String>()
        rule.alternativeElements
                .filter { it is ParserRuleCallElement && it.assignment.isEmpty() }
                .forEach {
                    val calledRule = getRuleByName(it.getBnfName())
                    calledRule?.let {
                        if (calledRule is ParserRule && calledRule.isPrivate) {
                            return@forEach
                        }
                        resultList.add(calledRule.name)
                        resultList.addAll(findDelegatedRuleNames(calledRule))
                    }
                }
        return resultList
    }


    private fun getRuleReturnType(rule: ModelRule): BridgeRuleType {
        if (rule.returnTypeText.isEmpty()) {
            if (rule.isDataTypeRule) {
                return BridgeRuleType("String", "")
            } else {
                return emfRegistry.findOrCreateType(rule.name)
            }
        } else {
            return emfRegistry.findOrCreateType(rule.returnTypeText)
        }
    }

    private fun isKeyword(text: String): Boolean {
        return text.startsWith("\"") || text.startsWith("\'")
    }


    private fun refactorRulesWithActions(rules: MutableList<ParserRule>, realRules: MutableList<ParserRule>? = null) {
//        val newRules = mutableListOf<ParserRule>()
        val changedElementNames = mutableListOf<Pair<String, String>>()
        rules.forEach { rule ->
            rule.alternativeElements
                    .filter { it.action.isNotEmpty() }
//                    .forEach {
//                        if (it.refactoredName == null && !isUniqueElementInRule(rule, it)) {
//                            val oldName = it.getBnfName()
//                            val newName = if (isUniqueElementInRule(rule, it)) oldName else createRefactoredName()
//                            it.refactoredName = newName
//                            val originRule = getRuleByName(oldName, rules)
//                            val newParserRule = if (originRule == null) createParserRuleWithExtension(newName, "", oldName) else createParserRuleWithExtension(newName, originRule.returnTypeText, oldName)
//                            newParserRule.isDataTypeRule = isDataTypeRuleOrKeyword(oldName, rules)
//
//                            newRules.add(newParserRule)
//                        }
//                    }
                    .forEach {
                        if (refactorOnActionNeeded(rule, it)) {
                            val oldName = it.getBnfName()
                            val newName = createRefactoredName()
                            it.refactoredName = newName
                            changedElementNames.add(Pair(oldName, newName))
//                            val originRule = getRuleByName(oldName, rules)
//                            val newParserRule = if (originRule == null) createParserRuleWithExtension(newName, "", oldName) else createParserRuleWithExtension(newName, originRule.returnTypeText, oldName)
//                            newParserRule.isDataTypeRule = isDataTypeRuleOrKeyword(oldName, rules)
//
//                            newRules.add(newParserRule)
                        }
                    }
        }

        realRules?.let {
            createNewRulesAccordingToChangedElements(realRules, changedElementNames)
//            realRules.addAll(newRules)
        } ?: kotlin.run {
            createNewRulesAccordingToChangedElements(rules, changedElementNames)
//            rules.addAll(newRules)
        }

    }


    private fun refactorOnActionNeeded(context: ParserRule, element: RuleElement): Boolean {
        val elementName = element.getBnfName()
        val actionText = element.action
        return context.alternativeElements.any { it.getBnfName() == elementName && it.action != actionText }
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


    private fun refactorRulesIfAssignmentCollision(rules: MutableList<ParserRule>, realRules: MutableList<ParserRule>? = null) {
        val changedElementNames = mutableListOf<Pair<String, String>>()
        rules.forEach { rule ->
            val elementsToChange = findRuleElementsToRenameInParserRule(rule)
            if (elementsToChange.isNotEmpty()) {
//                val leafsToChangeInRule = mutableMapOf<LeafPsiElement, String>()
                elementsToChange.forEach {
                    var newElementName = ""
//                    var newElementName = "${it.getBnfName().capitalize()}${rule.name.capitalize()}${createAssignmentName(it.assignment).capitalize()}"
                    if (it is ParserRuleCallElement || it is ParserCrossReferenceElement) {
                        val calledRule = getRuleByName(it.getBnfName(), rules)
                        calledRule?.let {
                            val number = ruleNameToRefactoringsNumber[calledRule.name]!!
                            newElementName = "${calledRule.name}${number + 1}"
                            ruleNameToRefactoringsNumber.put(calledRule.name, number + 1)
                            if (it is TerminalRule) {
                                newElementName = nameGenerator.toGKitTypesName(newElementName)
                            }

                        }
                        changedElementNames.add(Pair(it.getBnfName(), newElementName))
//                        leafsToChangeInRule.put(PsiTreeUtil.firstChild(it.psiElement) as LeafPsiElement, it.getBnfName())
                    } else {
                        newElementName = createRefactoredName()
                        val newRule = createParserRuleWithExtension(newElementName, "", it.getBnfName())
                        newRule.isDataTypeRule = true
                        realRules?.add(newRule) ?: rules.add(newRule)
                    }
                    it.refactoredName = newElementName
                }
            }
        }
        realRules?.let {
            createNewRulesAccordingToChangedElements(realRules, changedElementNames)
        } ?: kotlin.run {
            createNewRulesAccordingToChangedElements(rules, changedElementNames)
        }
    }


    private fun createNewRulesAccordingToChangedElements(rules: MutableList<ParserRule>, changedElements: List<Pair<String, String>>) {
        val duplicatedRules = mutableListOf<String>()
        changedElements.forEach { (originName, newName) ->
            val bnfExtension = "extends=$originName"
            val originRule = getRuleByName(originName, rules)
            if (originRule is ParserRule && !originRule.isDataTypeRule) {
                if (!duplicatedRules.contains(originName)) {
                    duplicatedRules.add(originName)
                    val privateRule = createPrivateDuplicateRuleToOrigin(originRule)
                    rules.add(privateRule)
                    fragmentRules.add(privateRule)
                    replaceParserRuleBodyWithText(originRule, privateRule.name)
                    refactorInfoList.add(RefactorOnAssignmentInfo(originName, privateRule.name))
                }
                val newRuleExtension = if (originRule.returnTypeText.isEmpty()) originRule.name else originRule.returnTypeText
                val newParserRule = createParserRuleWithExtension(newName, newRuleExtension, "${originName}Private")
                newParserRule.bnfExtensionsStrings.add(bnfExtension)
                newParserRule.isDataTypeRule = originRule.isDataTypeRule
                rulesWithSuperClass.put(newName, originName)
                rules.add(newParserRule)
                refactorInfoList.first { it.originRuleName == originName }.duplicateRuleNames.add(newName)

            } else if (originRule is TerminalRule || originRule!!.isDataTypeRule) {
                val newRule = createParserRuleWithExtension(newName, "", originName)
                newRule.isDataTypeRule = true
                rules.add(newRule)
            }
        }
    }

    private fun replaceParserRuleBodyWithText(rule: ParserRule, text: String) {
        val stubElement = ParserRuleCallElement(XtextElementFactory.createValidID(text))
        rule.alternativeElements.clear()
        rule.alternativeElements.add(stubElement)
    }

    private fun createParserRuleWithExtension(ruleName: String, extension: String, ruleBody: String): ParserRule {
        val xtextRule = XtextElementFactory.createParserRule("$ruleName ${if (extension.isNotEmpty()) "returns $extension" else ""} : ${ruleBody};")
        val newRule = parserRuleCreator.createRule(xtextRule)!!.getRule()
        return newRule
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

    private fun createSimpleActionFromText(text: String, psiElementName: String): BridgeSimpleAction {
        var actionName = text.removePrefix("{").removeSuffix("}")
        return BridgeSimpleAction(emfRegistry.findOrCreateType(actionName), psiElementName)
    }

    private fun createRewriteFromText(text: String, psiElementType: String, returnType: BridgeRuleType): Rewrite {
        var className = text.split(".")[0]
        className = className.removePrefix("{")
        var textFragmentForAssignment = text.split(".")[1]
        textFragmentForAssignment = textFragmentForAssignment.removeSuffix("current}")
        return Rewrite(emfRegistry.findOrCreateType(className),
                Assignment.fromString(textFragmentForAssignment), psiElementType, returnType)
    }

    private fun getBridgeCrossReferences(): List<BridgeCrossReference> {
        val resultList = mutableListOf<BridgeCrossReference>()
        crossReferences.distinctBy { it.name to it.containerName }.forEach {
            resultList.add(createBridgeCrossReferenceFromCrossReferenceElement(it))
        }
        return resultList.distinctBy { it.psiElementName to it.container.name }
    }

    private fun createBridgeCrossReferenceFromCrossReferenceElement(crossReferenceElement: ParserCrossReferenceElement): BridgeCrossReference {
        val containerRule = getRuleByName(crossReferenceElement.containerName)
        val containerType = getRuleReturnType(containerRule!!)
        return BridgeCrossReference(Assignment.fromString(crossReferenceElement.assignment),
                containerType,
                emfRegistry.findOrCreateType(crossReferenceElement.referenceTargetText),
                crossReferenceElement.name.replace("_", ""))
    }


    private fun addParserRulesForCrossReferences(rules: MutableList<ParserRule>) {
        val newRules = mutableListOf<ParserRule>()
        val crossReferences = rules
                .flatMap { it.alternativeElements }
                .filterIsInstance<ParserCrossReferenceElement>()
                .toList()
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


    private fun refactorComplicatedDeligateRules(oldRules: List<XtextParserRule>): List<XtextParserRule> {
        val refactoredRules = mutableListOf<XtextParserRule>()
        oldRules.forEach {
            var ruleCopy = it.copy() as XtextParserRule
            val branchesToRefactor = findCompositeBranchesInRule(it)
            for (i: Int in 0..branchesToRefactor.size - 1) {
                val newRule = getNewRuleFromBrunch(it, branchesToRefactor[i])
                ruleCopy = refactorOldRule(ruleCopy, branchesToRefactor[i], newRule.ruleNameAndParams.text)
                refactoredRules.add(newRule)
            }
            refactoredRules.add(ruleCopy)
        }
        return refactoredRules
    }

    private fun refactorOldRule(oldRule: XtextParserRule, branchToReplaceWithRuleCall: XtextConditionalBranch, newRuleName: String): XtextParserRule {
        var ruleText = oldRule.text
        ruleText = ruleText.replace(branchToReplaceWithRuleCall.text, newRuleName)
        return XtextElementFactory.createParserRule(ruleText)
    }

    private fun getNewRuleFromBrunch(mainRule: XtextParserRule, branch: XtextConditionalBranch): XtextParserRule {
        val ruleName = mainRule.ruleNameAndParams.text.replace("^", "").capitalize()
        if (ruleNameToRefactoringsNumber[ruleName] == null) {
            ruleNameToRefactoringsNumber.put(ruleName, 0)
        }
        val num = ruleNameToRefactoringsNumber[ruleName]!!
        ruleNameToRefactoringsNumber.put(ruleName, num + 1)
        val newRuleName = "${ruleName}$num"
        val newRuleReturnType = mainRule.typeRef?.text ?: ruleName

        val newRule = XtextElementFactory.createParserRule("$newRuleName returns $newRuleReturnType : ${branch.text};")
        return newRule
    }

    private fun findCompositeBranchesInRule(rule: XtextParserRule): List<XtextConditionalBranch> {
        val complicatedBranches = mutableListOf<XtextConditionalBranch>()
        val alternatives = rule.alternatives
        if (alternatives.conditionalBranchList.size > 1) {
            alternatives.conditionalBranchList.forEach {
                if (!isSimpleBranch(it)) complicatedBranches.add(it)
            }
        }
        return complicatedBranches
    }


    private fun isSimpleBranch(conditionalBranch: XtextConditionalBranch): Boolean {
        val branchAbstractTokens = conditionalBranch.unorderedGroup?.groupList
                ?.flatMap { it.abstractTokenList }
                ?.map { it.abstractTokenWithCardinality }
        branchAbstractTokens?.let { tokens ->
            if (tokens.size == 1) {
                tokens[0]!!.abstractTerminal?.let {
                    it.ruleCall?.let { return true }
                    it.keyword?.let { return true }
                }
            }
        }
        return false
    }


}