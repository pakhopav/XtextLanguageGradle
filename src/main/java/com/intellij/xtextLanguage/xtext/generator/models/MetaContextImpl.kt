package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.Keyword
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesIsInstance
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl.*
import com.intellij.xtextLanguage.xtext.psi.*
import java.util.*
import kotlin.test.assertNotNull

class MetaContextImpl(xtextFiles: List<XtextFile>) : MetaContext {
    private val _rules = mutableListOf<TreeRule>()
    override val rules: List<TreeRule>
        get() = _rules.toList()


    private val _terminalRules = mutableListOf<TreeTerminalRule>()
    override val terminalRules: List<TreeTerminalRule>
        get() = _terminalRules


    override val keywords: List<Keyword>

    private val ruleCreator: RuleCreator
    private val ruleNameOccurrences = mutableMapOf<String, Int>()
    private var keywordRenamesNumber = 0


    init {
        keywords = createKeywords(xtextFiles)
        ruleCreator = RuleCreator(keywords, createRegistry(xtextFiles))
        _terminalRules.addAll(getTerminalRules(xtextFiles))
        _rules.addAll(getAllParserRules(xtextFiles))
    }


    override fun getRuleByName(name: String): TreeRule {
        _rules.firstOrNull { it.name == name }?.let { return it }
        terminalRules.firstOrNull { it.name == name }?.let { return it }
        throw Exception("Rule ${name} not found")
    }

    override fun findLiteralAssignmentsInRule(rule: TreeRule): List<TreeLeaf> {
        val resultList = mutableListOf<TreeLeaf>()
        if (rule is TreeEnumRule) return resultList
        val nodesWithAssignment = rule.filterNodesInSubtree { it is TreeLeaf && it.assignment != null }.map { it as TreeLeaf }
        nodesWithAssignment.forEach {
            if (it is TreeKeyword) resultList.add(it)
            else if (it is TreeRuleCall && referencesToLiteral(it)) {
                resultList.add(it)
            }
        }
        return resultList.distinctBy { it.getBnfName() to it.assignment }
    }

    override fun findObjectAssignmentsInRule(rule: TreeRule): List<TreeRuleCall> {
        val resultList = mutableListOf<TreeRuleCall>()
        if (rule is TreeEnumRule) return resultList
        val nodesWithAssignment = rule.filterNodesInSubtree { it is TreeLeaf && it.assignment != null }.map { it as TreeLeaf }
        nodesWithAssignment.forEach {
            if (it is TreeRuleCall && !referencesToLiteral(it)) {
                resultList.add(it)
            }
        }
        return resultList.distinctBy { it.getBnfName() to it.assignment }
    }

    private fun referencesToLiteral(ruleCall: TreeRuleCall): Boolean {
        return getRuleByName(ruleCall.getBnfName()).isDatatypeRule
    }


    //======================================================
    //                     init methods
    //======================================================

    private fun createRegistry(xtextFiles: List<XtextFile>): EmfModelRegistry {
        return EmfModelRegistry.forXtextFiles(xtextFiles)
    }

    private fun createKeywords(xtextFiles: List<XtextFile>): List<Keyword> {
        val keywordsCreator = KeywordsCreator()
        return keywordsCreator.createKeywords(xtextFiles)
    }

    private fun <T : PsiElement> findElementsOfTypeInFiles(xtextFiles: List<XtextFile>, type: Class<T>): List<T> {
        val mutableListOfTerminalRules = mutableListOf<T>()
        xtextFiles.forEach {
            mutableListOfTerminalRules.addAll(PsiTreeUtil.findChildrenOfType(it, type))
        }
        return mutableListOfTerminalRules
    }

    private fun getAllParserRules(xtextFiles: List<XtextFile>): List<TreeRule> {
        val psiParserRules = findElementsOfTypeInFiles(xtextFiles, XtextParserRule::class.java)
        val psiEnumRules = findElementsOfTypeInFiles(xtextFiles, XtextEnumRule::class.java)
        val rawParserRules = psiParserRules.map { ruleCreator.createParserRule(it) }.flatten().toMutableList()
        rawParserRules.addAll(psiEnumRules.map { ruleCreator.createEnumRule(it) })
        refactorRules(rawParserRules)
        return rawParserRules
    }

    private fun getTerminalRules(xtextFiles: List<XtextFile>): List<TreeTerminalRule> {
        val psiTerminalRules = findElementsOfTypeInFiles(xtextFiles, XtextTerminalRule::class.java)
        val rawTerminalRules = psiTerminalRules.map { ruleCreator.createTerminalRule(it) }
        rawTerminalRules.forEach {
            it.filterNodesIsInstance(TreeTerminalRuleCall::class.java).forEach { terminalRuleCall ->
                val calledRule = rawTerminalRules.firstOrNull { it.name == terminalRuleCall.calledRuleName }
                calledRule?.let {
                    terminalRuleCall.called = it
                }
            }
        }
        return rawTerminalRules
    }


    private fun refactorRules(rawRules: MutableList<TreeRule>) {
        initOccurrencesMap(rawRules)
        addRulesForCrossReferences(rawRules)
        setCalledFragmentRuleProperty(rawRules)
        checkReferencedRules(rawRules.filterIsInstance<TreeParserRuleImpl>())
        markDatatypeRules(rawRules)
        refactorOnAssignmentsCollision(rawRules)
        fixInheritanceOfNamedRules(rawRules)
    }

    private fun addRulesForCrossReferences(rules: MutableList<TreeRule>) {
        val newRules = mutableListOf<TreeRule>()
        val nodesWithCrossReferences = rules.flatMap { it.filterNodesIsInstance(TreeCrossReferenceImpl::class.java) }
        nodesWithCrossReferences.distinctBy { it.getBnfName() }.forEach {
            val newRule = createSimpleRule(it.getBnfName(), it.referenceType, EmfClassDescriptor.STRING)
            newRule.setIsDatatypeRule(true)
            newRules.add(newRule)
        }
        rules.addAll(newRules)
    }

    private fun createSimpleRule(ruleName: String, referencedRuleName: String, returnType: EmfClassDescriptor): TreeParserRuleImpl {
        val newRule = TreeParserRuleImpl(ruleName, returnType)
        val ruleCallNode = TreeRuleCallImpl(XtextElementFactory.createRuleCall(referencedRuleName), newRule, Cardinality.NONE)
        newRule.addChild(ruleCallNode)
        return newRule
    }

    private fun setCalledFragmentRuleProperty(rules: List<TreeRule>) {
        val fragmentRules = rules.filterIsInstance<TreeFragmentRule>()
        val fragmentsNames = fragmentRules.map { it.name }
        rules.forEach { rule ->
            val allRuleCalls = rule.filterNodesIsInstance(TreeRuleCallImpl::class.java)
            val ruleCallsToFragments = allRuleCalls.filter { fragmentsNames.contains(it.getBnfName()) }
            ruleCallsToFragments.forEach { ruleCall ->
                ruleCall.called = fragmentRules.first { it.name == ruleCall.getBnfName() }
            }
        }
    }

    private fun markDatatypeRules(rules: List<TreeRule>) {
        val dataTypeNames = mutableListOf<String>()
        dataTypeNames.addAll(_terminalRules.map { it.name })
        var namesListChanged = true
        val rulesToCheck = LinkedList<TreeRule>()
        val parserRules = mutableListOf<TreeRule>()
        parserRules.addAll(rules)
        while (namesListChanged) {
            namesListChanged = false
            rulesToCheck.addAll(parserRules)
            parserRules.clear()
            while (rulesToCheck.isNotEmpty()) {
                val nextRule = rulesToCheck.poll()
                if (isDatatypeRule(nextRule, dataTypeNames)) {
                    (nextRule as? TreeParserRuleImpl)?.setIsDatatypeRule(true)
                    (nextRule as? TreeFragmentRuleImpl)?.setIsDatatypeRule(true)
                    dataTypeNames.add(nextRule.name)
                    namesListChanged = true
                } else {
                    parserRules.add(nextRule)
                }
            }
        }
    }

    private fun refactorOnAssignmentsCollision(rules: MutableList<TreeRule>) {
        val newNames = mutableListOf<RefactoredName>()
        rules.forEach { root ->
            var leavesToRename = findEquallyCalledLeavesWithSameAssignment(root)
            if (leavesToRename.isEmpty()) return@forEach

            //previously generated names, grouped copy of @newNames, reassigned for each root
            val availableNames = mutableMapOf<String, MutableList<String>>()
            newNames.groupBy { it.oldName }.forEach {
                availableNames.put(it.key, it.value.map { it.newName }.toMutableList())
            }

            renameLeavesOnAssignment(leavesToRename, newNames, availableNames)

            leavesToRename = findLeavesWithRewriteCollision(root)
            if (leavesToRename.isEmpty()) return@forEach
            renameLeavesOnRewrite(leavesToRename, newNames, availableNames)

            leavesToRename = findLeavesWithSimpleActionCollision(root)
            if (leavesToRename.isEmpty()) return@forEach
            renameLeavesOnSimpleAction(leavesToRename, newNames, availableNames)
        }

        createNewRulesAccordingToChanges(rules, newNames)
    }

    private fun renameLeavesOnAssignment(leavesToRename: List<TreeLeaf>, newNames: MutableList<RefactoredName>, availableNames: Map<String, MutableList<String>>) {
        val groupedByName = leavesToRename.groupBy { it.getBnfName() }
        groupedByName.values.forEach {
            val oldName = it.first().getBnfName()
            val groupedByAssignment = it.groupBy { it.assignment }.map { it.value }
            renameLeaves(groupedByAssignment, oldName, newNames, availableNames)
        }
    }

    private fun renameLeavesOnRewrite(leavesToRename: List<TreeLeaf>, newNames: MutableList<RefactoredName>, availableNames: Map<String, MutableList<String>>) {
        val groupedByName = leavesToRename.groupBy { it.getBnfName() }
        groupedByName.values.forEach {
            val oldName = it.first().getBnfName()
            val groupedByRewrite = it.groupBy { it.rewrite }.map { it.value }
            renameLeaves(groupedByRewrite, oldName, newNames, availableNames)
        }
    }

    private fun renameLeavesOnSimpleAction(leavesToRename: List<TreeLeaf>, newNames: MutableList<RefactoredName>, availableNames: Map<String, MutableList<String>>) {
        val groupedByName = leavesToRename.groupBy { it.getBnfName() }
        groupedByName.values.forEach {
            val oldName = it.first().getBnfName()
            val groupedBySimpleAction = it.groupBy { it.simpleAction }.map { it.value }
            renameLeaves(groupedBySimpleAction, oldName, newNames, availableNames)
        }
    }

    private fun renameLeaves(leavesToRename: List<List<TreeLeaf>>, oldName: String, newNames: MutableList<RefactoredName>, availableNames: Map<String, MutableList<String>>) {
        leavesToRename.forEach { leavesToRefactor ->
            val newName: String
            val previouslyCreatedNames = availableNames.get(oldName)
            if (previouslyCreatedNames != null && previouslyCreatedNames.isNotEmpty()) {
                newName = previouslyCreatedNames[0]
                previouslyCreatedNames.removeAt(0)
            } else {
                newName = createNewNameForLeaf(leavesToRefactor[0])
                newNames.add(RefactoredName(oldName, newName))
            }
            leavesToRefactor.forEach {
                it as TreeLeafImpl
                val newTreeRuleCall = createNewTreeRuleCallToReplaceLeaf(it, newName)
                (it.parent as TreeNodeImpl).replaceChild(it, newTreeRuleCall)
            }
        }
    }

    private fun createNewTreeRuleCallToReplaceLeaf(leafToReplace: TreeLeafImpl, newNodeName: String): TreeRuleCall {
        val oldLeafCardinality = leafToReplace.cardinality
        val psiRuleCall = XtextElementFactory.createRuleCall(newNodeName)
        val newTreeRuleCall = TreeRuleCallImpl(psiRuleCall, leafToReplace.parent!!, oldLeafCardinality, leafToReplace.assignment)
        leafToReplace.rewrite?.let { newTreeRuleCall.setRewrite(it) }
        leafToReplace.simpleAction?.let { newTreeRuleCall.setSimpleAction(it) }
        return newTreeRuleCall
    }

    private fun findEquallyCalledLeavesWithSameAssignment(rule: TreeRule): List<TreeLeaf> {
        val result = mutableListOf<TreeLeaf>()
        val groupedElements = rule.filterNodesIsInstance(TreeLeaf::class.java).groupBy { it.getBnfName() }
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

    private fun createNewNameForLeaf(node: TreeLeaf): String {
        val newName: String
        if (node is TreeRuleCall || node is TreeCrossReference) {
            val n = ruleNameOccurrences.get(node.getBnfName())
            assertNotNull(n)
            newName = node.getBnfName() + (n + 1).toString()
            ruleNameOccurrences.put(node.getBnfName(), n + 1)
        } else {
            newName = "GeneratedRule${keywordRenamesNumber++}"
        }
        return newName
    }

    private fun findLeavesWithRewriteCollision(rule: TreeRule): List<TreeLeaf> {
        val resultList = mutableListOf<TreeLeaf>()
        val allLeaves = rule.filterNodesIsInstance(TreeLeaf::class.java)
        val leavesWithRewrite = allLeaves.filter { it.rewrite != null }
        leavesWithRewrite.forEach { leafWithRewrite ->
            if (allLeaves.any { it.getBnfName() == leafWithRewrite.getBnfName() && it.rewrite != leafWithRewrite.rewrite }) {
                resultList.add(leafWithRewrite)
            }
        }

        return resultList
    }

    private fun findLeavesWithSimpleActionCollision(rule: TreeRule): List<TreeLeaf> {
        val resultList = mutableListOf<TreeLeaf>()
        val allLeaves = rule.filterNodesIsInstance(TreeLeaf::class.java)
        val leavesWithSimpleAction = allLeaves.filter { it.simpleAction != null }
        leavesWithSimpleAction.forEach { leafWithSimpleAction ->
            if (allLeaves.any { it.getBnfName() == leafWithSimpleAction.getBnfName() && it.simpleAction != leafWithSimpleAction.simpleAction }) {
                resultList.add(leafWithSimpleAction)
            }
        }
        return resultList
    }

    private fun createNewRulesAccordingToChanges(rules: MutableList<TreeRule>, changes: List<RefactoredName>) {
        val rulesWithDuplicate = mutableListOf<String>()
        changes.forEach {
            proceedNewRuleCreation(rules, it, rulesWithDuplicate)
        }
    }


    private fun proceedNewRuleCreation(rules: MutableList<TreeRule>, refactoredName: RefactoredName, rulesWithDuplicate: List<String>) {
        val originRule = rules.firstOrNull { it.name == refactoredName.oldName }
        if (originRule != null && !originRule.isDatatypeRule) {
            originRule as TreeParserRuleImpl
            if (!rulesWithDuplicate.contains(originRule.name)) {
                val privateRuleName = "${originRule.name}Private"

                val privateRule = TreeFragmentRuleImpl(privateRuleName)
                rules.add(rules.indexOf(originRule) + 1, privateRule)
                originRule.children.forEach {
                    it as TreeNodeImpl
                    privateRule.addChild(it)
                    it.parent = privateRule
                }
                originRule.removeAll(originRule.children as List<TreeNodeImpl>)
                val psiRuleCall = XtextElementFactory.createRuleCall(privateRuleName)
                val treeRuleCall = TreeRuleCallImpl(psiRuleCall, originRule, Cardinality.NONE)
                treeRuleCall.called = privateRule
                originRule.addChild(treeRuleCall)
            }
            val originRuleFirstChild = originRule.children.firstOrNull()
            val fragmentDuplicateRule = (originRuleFirstChild as? TreeRuleCall)?.getCalledRule()
            assertNotNull(fragmentDuplicateRule)
            val newRule = TreeDuplicateRuleImpl(refactoredName.newName, originRule.returnType, originRule.name)
            newRule.setSuperRule(refactoredName.oldName)
            newRule.setIsDatatypeRule(originRule.isDatatypeRule)
            val psiRuleCall = XtextElementFactory.createRuleCall(fragmentDuplicateRule.name)
            val ruleCallNode = TreeRuleCallImpl(psiRuleCall, newRule, Cardinality.NONE)
            ruleCallNode.called = fragmentDuplicateRule
            newRule.addChild(ruleCallNode)
            rules.add(rules.indexOf(originRule) + 1, newRule)
        } else {
            val newRule = createSimpleRule(refactoredName.newName, refactoredName.oldName, EmfClassDescriptor.STRING)
            newRule.setIsDatatypeRule(true)
            rules.add(newRule)
        }
    }

    private fun isDatatypeRule(rule: TreeRule, datatypeNames: List<String>): Boolean {
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
                || leaf.simpleAction != null
    }

    private fun initOccurrencesMap(parserRules: List<TreeRule>) {
        parserRules.forEach { ruleNameOccurrences.putIfAbsent(it.name, 0) }
        terminalRules.forEach { ruleNameOccurrences.putIfAbsent(it.name, 0) }
    }

    private fun checkReferencedRules(rules: List<TreeParserRuleImpl>) {
        val allCrossReferenceNodes = rules
                .flatMap { it.filterNodesIsInstance(TreeCrossReference::class.java) }
                .distinctBy { it.getBnfName() }
        allCrossReferenceNodes.forEach { crossReferenceNode ->
            rules.filter { it.returnType == crossReferenceNode.targetType }.forEach {
                it.setIsReferenced(true)
            }
        }
    }

    private fun fixInheritanceOfNamedRules(rules: MutableList<TreeRule>) {
        val crossReferencesNodes = rules.flatMap { it.filterNodesInSubtree { it is TreeCrossReference } }.map { it as TreeCrossReference }
        val targetRules = mutableListOf<TreeParserRuleImpl>()
        crossReferencesNodes.distinctBy { it.getBnfName() }.forEach { treeCrossReference ->
            val targeted = rules.filterIsInstance<TreeParserRule>()
                    .filter { it.returnType == treeCrossReference.targetType }
                    .map { it as TreeParserRuleImpl }
            targetRules.addAll(targeted)
        }
        targetRules.forEach { targetRule ->
            if (!targetRule.hasNameFeature()) {
                val calledRules = findRulesCalledWithoutAssignments(targetRule, rules)
                calledRules.forEach { fixInheritanceOfNamedRule(it, targetRule.name, rules) }
            }
        }
    }

    private fun findRulesCalledWithoutAssignments(rule: TreeParserRuleImpl, allRules: List<TreeRule>): Set<TreeParserRuleImpl> {
        val resultSet = mutableSetOf<TreeParserRuleImpl>()
        val ruleCallsWithoutAssignment = rule.filterNodesInSubtree { it is TreeRuleCall && it.assignment == null }.map { it as TreeRuleCall }
        ruleCallsWithoutAssignment.forEach { ruleCallNode ->
            allRules.firstOrNull { it.name == ruleCallNode.getBnfName() }?.let {
                resultSet.add(it as TreeParserRuleImpl)
            }
        }
        return resultSet
    }

    private fun fixInheritanceOfNamedRule(rule: TreeParserRuleImpl, parentRuleName: String, rules: List<TreeRule>): Boolean {
        if (rule.hasNameFeature()) {
            rule.setSuperRule(parentRuleName)
            return true
        } else {
            var anyNameFoundInCalledRules = false
            val calledRules = findRulesCalledWithoutAssignments(rule, rules)
            calledRules.forEach {
                if (fixInheritanceOfNamedRule(it, rule.name, rules)) anyNameFoundInCalledRules = true
            }
            if (anyNameFoundInCalledRules) {
                rule.setSuperRule(parentRuleName)
                return true
            }
        }
        return false
    }
    private data class RefactoredName(val oldName: String, val newName: String)
}