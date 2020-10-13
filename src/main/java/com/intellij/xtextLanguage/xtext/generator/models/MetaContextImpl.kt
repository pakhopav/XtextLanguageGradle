package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.models.elements.EnumRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRuleCallElelment
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRuleElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl.TreeCrossReferenceImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl.TreeNodeImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl.TreeRootImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl.TreeRuleCallImpl
import com.intellij.xtextLanguage.xtext.generator.models.exception.TypeNotFoundException
import com.intellij.xtextLanguage.xtext.psi.*
import java.util.*
import kotlin.test.assertNotNull

class MetaContextImpl(xtextFiles: List<XtextFile>) : MetaContext {
    private val _parserRules = mutableListOf<TreeRoot>()
    override val parserRules: List<TreeRoot>
        get() = _parserRules.toList()

    private val _terminalRules = mutableListOf<TerminalRule>()
    override val terminalRules: List<TerminalRule>
        get() = _terminalRules.toList()

    private val _enumRules = mutableListOf<EnumRule>()
    override val enumRules: List<EnumRule>
        get() = _enumRules.toList()


    override val keywordModel = createKeywordModel(xtextFiles)

    private val keywords = keywordModel.keywords
    private val emfRegistry: EmfModelRegistry
    private val ruleCreator = ParserRuleCreator(keywords)
    private val ruleNameOccurrences = mutableMapOf<String, Int>()
    private var numberOfRulesCreatedBecauseOfRefactor = 0
    private val refactoredRulesWithDuplicates = mutableMapOf<String, MutableList<String>>()
    private var referencedRuleNames: List<String>? = null


    init {
        emfRegistry = createRegistry(xtextFiles)
        _terminalRules.addAll(getAllTerminalRules(xtextFiles))
        _enumRules.addAll(getAllEnumRules(xtextFiles))
        _parserRules.addAll(getAllParserRules(xtextFiles))
    }


    override fun getParserRuleByName(name: String): TreeRoot? {
        return _parserRules.firstOrNull { it.name == name }
    }

    override fun getClassDescriptionByName(typeName: String): EmfClassDescriptor {

        return emfRegistry.findOrCreateType(typeName) ?: throw TypeNotFoundException(typeName)
    }

    override fun getRuleReturnType(rule: TreeRoot): EmfClassDescriptor {
        val returnTypeText = if (rule.returnTypeText.isEmpty()) rule.name else rule.returnTypeText
        if (returnTypeText == "String") return EmfClassDescriptor.STRING
        val ruleType = emfRegistry.findOrCreateType(returnTypeText)
        if (ruleType == null) throw TypeNotFoundException(returnTypeText)
        return ruleType
    }

    override fun isReferencedRule(rule: TreeRoot): Boolean {
        if (referencedRuleNames == null) referencedRuleNames = getReferencedNames()
        return referencedRuleNames!!.contains(rule.name)
    }

    override fun isDuplicateRule(rule: TreeRoot): Boolean {
        return refactoredRulesWithDuplicates.values.flatten().contains(rule.name)
    }

    override fun hasPrivateDuplicate(rule: TreeRoot): Boolean {
        return refactoredRulesWithDuplicates.keys.contains(rule.name)
    }

    override fun findLiteralAssignmentsInRule(rule: TreeRoot): List<TreeLeaf> {
        val resultList = mutableListOf<TreeLeaf>()
        val nodesWithAssignment = rule.filterNodesInSubtree { it is TreeLeaf && it.assignment != null }.map { it as TreeLeaf }
        nodesWithAssignment.forEach {
            if (it is TreeKeyword) resultList.add(it)
            else if (it is TreeRuleCall && referencesToDatatypeOrTerminalRule(it)) {
                resultList.add(it)
            }
        }
        return resultList
    }

    override fun findObjectAssignmentsInRule(rule: TreeRoot): List<TreeRuleCall> {
        val resultList = mutableListOf<TreeRuleCall>()
        val nodesWithAssignment = rule.filterNodesInSubtree { it is TreeLeaf && it.assignment != null }.map { it as TreeLeaf }
        nodesWithAssignment.forEach {
            if (it is TreeRuleCall && !referencesToDatatypeOrTerminalRule(it)) {
                resultList.add(it)
            }
        }
        return resultList
    }

    private fun referencesToDatatypeOrTerminalRule(ruleCall: TreeRuleCall): Boolean {
        getParserRuleByName(ruleCall.getBnfName())?.let {
            return it.isDatatypeRule
        } ?: return true
    }

    //======================================================
    //                     init methods
    //======================================================

    private fun createRegistry(xtextFiles: List<XtextFile>): EmfModelRegistry {
        return EmfModelRegistry.forXtextFiles(xtextFiles)
    }

    private fun createKeywordModel(xtextFiles: List<XtextFile>): XtextKeywordModel {
        return XtextKeywordModel(findElementsOfTypeInFiles(xtextFiles, XtextAbstractRule::class.java))
    }

    private fun getAllTerminalRules(xtextFiles: List<XtextFile>): List<TerminalRule> {
        val resultList = findElementsOfTypeInFiles(xtextFiles, XtextTerminalRule::class.java).map { TerminalRule(it) }
        finishTerminalRuleCalls(resultList)
        return resultList
    }

    private fun finishTerminalRuleCalls(rules: List<TerminalRule>) {
        rules.forEach { rule ->
            rule.alternativeElements.filterIsInstance<TerminalRuleCallElelment>().forEach { ruleElement ->
                rules.firstOrNull { it.name == ruleElement.psiElement.text }?.let { resolvedRule ->
                    ruleElement.setFlexName(resolvedRule.alternativeElements.map { (it as TerminalRuleElement).getFlexName() }.joinToString(separator = ""))
                    ruleElement.setBnfName(resolvedRule.alternativeElements.map { it.getBnfName() }.joinToString(separator = ""))
                }
            }
        }
    }

    private fun getAllEnumRules(xtextFiles: List<XtextFile>): List<EnumRule> {
        return findElementsOfTypeInFiles(xtextFiles, XtextEnumRule::class.java).map { EnumRule(it) }
    }


    private fun <T : PsiElement> findElementsOfTypeInFiles(xtextFiles: List<XtextFile>, type: Class<T>): List<T> {
        val mutableListOfTerminalRules = mutableListOf<T>()
        xtextFiles.forEach {
            mutableListOfTerminalRules.addAll(PsiTreeUtil.findChildrenOfType(it, type))
        }
        return mutableListOfTerminalRules
    }

    private fun getAllParserRules(xtextFiles: List<XtextFile>): List<TreeRoot> {
        val psiRules = findElementsOfTypeInFiles(xtextFiles, XtextParserRule::class.java)
        val refactoredPsiRules = refactorComplicatedDeligateRules(psiRules)
        val rawParserRules = refactoredPsiRules.map { ruleCreator.createFromXtextParserRule(it) }.map { it as TreeRoot }.toMutableList()
        refactorRules(rawParserRules)
        return rawParserRules
    }

    private fun refactorRules(rawRules: MutableList<TreeRoot>) {
        initOccurrencesMap(rawRules)
        addRulesForCrossReferences(rawRules)
        inlineFragments(rawRules)
        markDatatypeRules(rawRules)
        refactorOnAssignmentsCollision(rawRules)
        refactorOnActionsCollision(rawRules)
        fixInheritanceOfNamedRules(rawRules)
    }

    private fun addRulesForCrossReferences(rules: MutableList<TreeRoot>) {
        val newRules = mutableListOf<TreeRoot>()
        val nodesWithCrossReferences = rules.flatMap { findAllNodesOfType(it, TreeCrossReferenceImpl::class.java) }
        nodesWithCrossReferences.distinctBy { it.getBnfName() }.forEach {
            val newRule = ruleCreator.createRule(it.getBnfName(), it.referenceType, "String")
            newRule.setIsDatatypeRule(true)
            newRules.add(newRule)
        }
        rules.addAll(newRules)
    }

    private fun inlineFragments(rules: MutableList<TreeRoot>) {
        val fragmentRules = rules.filter { it.isFragment }
        val fragmentsNames = fragmentRules.map { it.name }
        rules.forEach { rule ->
            val allRuleCalls = findAllNodesOfType(rule, TreeRuleCallImpl::class.java)
            val ruleCallsToFragments = allRuleCalls.filter { fragmentsNames.contains(it.getBnfName()) }
            ruleCallsToFragments.forEach { ruleCall ->
                val calledFragmentRule = fragmentRules.first { it.name == ruleCall.getBnfName() }
                val calledFragmentCopy = (calledFragmentRule as TreeNodeImpl).copy()
                (ruleCall.parent as TreeNodeImpl).replaceChild(ruleCall, calledFragmentCopy)
                calledFragmentCopy.setSpecificBnfString(ruleCall.getBnfName())
            }
        }
    }

    private fun markDatatypeRules(rules: List<TreeRoot>) {
        val dataTypeNames = mutableListOf<String>()
        dataTypeNames.addAll(terminalRules.map { it.name })
        var namesListChanged = true
        val rulesToCheck = LinkedList<TreeRoot>()
        val parserRules = mutableListOf<TreeRoot>()
        parserRules.addAll(rules)
        while (namesListChanged) {
            namesListChanged = false
            rulesToCheck.addAll(parserRules)
            parserRules.clear()
            while (rulesToCheck.isNotEmpty()) {
                val nextRule = rulesToCheck.poll() as TreeRootImpl
                if (isDatatypeRule(nextRule, dataTypeNames)) {
                    nextRule.setIsDatatypeRule(true)
                    dataTypeNames.add(nextRule.name)
                    namesListChanged = true
                } else {
                    parserRules.add(nextRule)
                }
            }
        }
    }

    private fun refactorOnAssignmentsCollision(rules: MutableList<TreeRoot>) {
        // [old name : new name] pairs
        val changedNames = mutableListOf<Pair<String, String>>()

        rules.forEach { root ->
            val nodesToRename = findNodesToRenameIfSameAssignment(root)
            nodesToRename.forEach {
                val newName = createNewNameForNode(it)
                val oldName = it.getBnfName()
                it.setSpecificBnfString(newName)
                changedNames.add(Pair(oldName, newName))
            }
        }
        createNewRulesAccordingToChanges(rules, changedNames)
    }

    private fun findNodesToRenameIfSameAssignment(rule: TreeRoot): List<TreeLeaf> {
        val result = mutableListOf<TreeLeaf>()
        val groupedElements = findAllNodesOfType(rule, TreeLeaf::class.java).groupBy { it.getBnfName() }
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


    private fun createNewNameForNode(node: TreeNode): String {
        val newName: String
        if (node is TreeRuleCallImpl || node is TreeCrossReferenceImpl) {
            node as TreeLeaf
            val n = ruleNameOccurrences.get(node.getBnfName())
            assertNotNull(n)
            newName = node.getBnfName() + (n + 1).toString()
            ruleNameOccurrences.put(node.getBnfName(), n + 1)
        } else {
            newName = "GeneratedRule${numberOfRulesCreatedBecauseOfRefactor++}"
        }
        return newName
    }


    private fun refactorOnActionsCollision(rules: MutableList<TreeRoot>) {
        val changedNames = mutableListOf<Pair<String, String>>()
        rules.forEach {
            val nodesToRename = findNodesToRenameIfSameAction(it)
            nodesToRename.forEach {
                val newName = createNewNameForNode(it)
                val oldName = it.getBnfName()
                it.setSpecificBnfString(newName)
                changedNames.add(Pair(oldName, newName))
            }
        }

        createNewRulesAccordingToChanges(rules, changedNames)
    }

    private fun findNodesToRenameIfSameAction(rule: TreeRoot): List<TreeLeaf> {
        val resultList = mutableListOf<TreeLeaf>()
        val allLeafs = findAllNodesOfType(rule, TreeLeaf::class.java)
        val leafsWithRewrite = allLeafs.filter { it.rewrite != null }
        leafsWithRewrite.forEach { leafWithRewrite ->
            if (allLeafs.any { it.getBnfName() == leafWithRewrite.getBnfName() && it.rewrite != leafWithRewrite.rewrite }) {
                resultList.add(leafWithRewrite)
            }
        }
        val leafsWithSimpleAction = allLeafs.filter { it.simpleActionText != null }
        leafsWithSimpleAction.forEach { leafWithSimpleAction ->
            if (allLeafs.any { it.getBnfName() == leafWithSimpleAction.getBnfName() && it.simpleActionText != leafWithSimpleAction.simpleActionText }) {
                resultList.add(leafWithSimpleAction)
            }
        }
        return resultList
    }

    private fun createNewRulesAccordingToChanges(rules: MutableList<TreeRoot>, changes: List<Pair<String, String>>) {
        val rulesWithDuplicate = mutableListOf<String>()
        changes.forEach {
            proceedNewRuleCreation(rules, it, rulesWithDuplicate)
        }
    }


    private fun proceedNewRuleCreation(rules: MutableList<TreeRoot>, pair: Pair<String, String>, rulesWithDuplicate: List<String>) {
        val originRule = rules.firstOrNull { it.name == pair.first }
        if (originRule != null && !originRule.isDatatypeRule) {
            originRule as TreeRootImpl
            if (!rulesWithDuplicate.contains(originRule.name)) {
                val privateRuleName = "${originRule.name}Private"
                originRule.setSpecificBnfString(privateRuleName)
                refactoredRulesWithDuplicates.put(originRule.name, mutableListOf())
            }
            val newRuleReturnTypeText = if (originRule.returnTypeText.isEmpty()) originRule.name else originRule.returnTypeText
            val newRule = ruleCreator.createRule(pair.second, "${pair.first}Private", newRuleReturnTypeText, originRuleName = originRule.name)
            newRule.setSuperRule(pair.first)
            newRule.setIsDatatypeRule(originRule.isDatatypeRule)
            rules.add(rules.indexOf(originRule) + 1, newRule)
            refactoredRulesWithDuplicates.get(originRule.name)?.add(newRule.name)
        } else {
            val newRule = ruleCreator.createRule(pair.second, pair.first)
            newRule.setIsDatatypeRule(true)
            rules.add(newRule)
        }
    }

    private fun isDatatypeRule(rule: TreeRoot, datatypeNames: List<String>): Boolean {
        val leaves = rule.filterNodesInSubtree { it is TreeLeaf }.map { it as TreeLeaf }
        leaves.forEach {
            if (notAllowedInDatatypeRule(it, datatypeNames)) return false
        }
        return true
    }

    private fun notAllowedInDatatypeRule(leaf: TreeLeaf, datatypeNames: List<String>): Boolean {
        return (leaf is TreeRuleCall && !datatypeNames.contains(leaf.getBnfName()))
                || leaf.assignment != null
                || leaf.rewrite != null
                || leaf.simpleActionText != null
    }

    private fun <T : TreeNode> findAllNodesOfType(rootNode: TreeNode, type: Class<T>): List<T> {
        val resultList = mutableListOf<T>()
        rootNode.children.forEach {
            resultList.addAll(findAllNodesOfType(it, type))
        }
        if (type.isInstance(rootNode)) resultList.add(rootNode as T)
        return resultList
    }

    private fun initOccurrencesMap(parserRules: List<TreeRoot>) {
        parserRules.forEach { ruleNameOccurrences.putIfAbsent(it.name, 0) }
        terminalRules.forEach { ruleNameOccurrences.putIfAbsent(it.name, 0) }
        enumRules.forEach { ruleNameOccurrences.putIfAbsent(it.name, 0) }
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
        if (ruleNameOccurrences[ruleName] == null) {
            ruleNameOccurrences.put(ruleName, 0)
        }
        val num = ruleNameOccurrences[ruleName]!!
        ruleNameOccurrences.put(ruleName, num + 1)
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

    private fun getReferencedNames(): List<String> {
        val resultList = mutableListOf<String>()
        val treeCrossReference = _parserRules
                .flatMap { findAllNodesOfType(it, TreeCrossReference::class.java) }
                .distinctBy { it.getBnfName() }
        treeCrossReference.forEach { nodeWithReference ->
            resultList.addAll(_parserRules
                    .filter { getRuleReturnType(it) == getClassDescriptionByName(nodeWithReference.targetTypeText) }
                    .map { it.name })
        }
        return resultList.distinct()
    }


    private fun fixInheritanceOfNamedRules(rules: MutableList<TreeRoot>) {
        val crossReferencesNodes = rules.flatMap { it.filterNodesInSubtree { it is TreeCrossReference } }.map { it as TreeCrossReference }
        val targetRules = mutableListOf<TreeRootImpl>()
        crossReferencesNodes.distinctBy { it.getBnfName() }.forEach { treeCrossReference ->
            targetRules.addAll(rules.filter { getRuleReturnType(it) == getClassDescriptionByName(treeCrossReference.targetTypeText) }.map { it as TreeRootImpl })
        }
        targetRules.forEach { targetRule ->
            if (!targetRule.hasName()) {
                val calledRules = findRulesCalledWithoutAssignments(targetRule, rules)
                calledRules.forEach { fff(it, targetRule.name, rules) }
            }
        }
    }

    private fun findRulesCalledWithoutAssignments(rule: TreeRootImpl, allRules: List<TreeRoot>): Set<TreeRootImpl> {
        val resultSet = mutableSetOf<TreeRootImpl>()
        val ruleCallsWithoutAssignment = rule.filterNodesInSubtree { it is TreeRuleCall && it.assignment == null }.map { it as TreeRuleCall }
        ruleCallsWithoutAssignment.forEach { ruleCallNode ->
            allRules.firstOrNull { it.name == ruleCallNode.getBnfName() }?.let {
                resultSet.add(it as TreeRootImpl)
            }
        }
        return resultSet
    }

    private fun fff(rule: TreeRootImpl, parentRuleName: String, rules: List<TreeRoot>): Boolean {
        if (rule.hasName()) {
            rule.setSuperRule(parentRuleName)
            return true
        } else {
            var anyNameFoundInCalledRules = false
            val calledRules = findRulesCalledWithoutAssignments(rule, rules)
            calledRules.forEach {
                if (fff(it, rule.name, rules)) anyNameFoundInCalledRules = true
            }
            if (anyNameFoundInCalledRules) {
                rule.setSuperRule(parentRuleName)
                return true
            }
        }
        return false
    }
}