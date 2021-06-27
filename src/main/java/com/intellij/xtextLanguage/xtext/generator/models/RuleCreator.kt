package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.Keyword
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.TreeRewrite
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl.*
import com.intellij.xtextLanguage.xtext.psi.*
import java.util.*
import kotlin.test.assertNotNull

class RuleCreator(keywords: List<Keyword>, emfRegistry: EmfModelRegistry) {
    private val visitor = XtextParserRuleVisitor(keywords, emfRegistry)
    private val terminalRuleVisitor = XtextTerminalRuleVisitor(emfRegistry)


    fun createEnumRule(psiRule: XtextEnumRule): TreeRule {
        return visitor.createTreeFromXtextEnumRule(psiRule)
    }

    fun createTerminalRule(psiRule: XtextTerminalRule): TreeTerminalRule {
        return terminalRuleVisitor.createTreeFromXtextTerminalRule(psiRule)
    }

    fun createParserRule(xtextRule: XtextParserRule): List<TreeRule> {
        return visitor.createTreesFromXtextParserRule(xtextRule)
    }


    private class XtextTerminalRuleVisitor(private val emfRegistry: EmfModelRegistry) : XtextVisitor() {
        private val treeNodeStack = Stack<TreeNodeImpl>()
        private var cardinality: Cardinality = Cardinality.NONE

        private fun getRuleTypeDescriptor(rule: XtextTerminalRule): EmfClassDescriptor {
            val returnTypeText = rule.typeRef?.text ?: rule.validID.text.eliminateCaret()
            val ruleType = emfRegistry.findOrCreateType(returnTypeText)
            if (ruleType == null) return EmfClassDescriptor.STRING
            return ruleType
        }

        private fun clearAll() {
            treeNodeStack.clear()
            cardinality = Cardinality.NONE
        }

        fun createTreeFromXtextTerminalRule(rule: XtextTerminalRule): TreeTerminalRule {
            clearAll()
            val type = getRuleTypeDescriptor(rule)
            val treeRoot = TreeTerminalRuleImpl(rule, type)
            treeNodeStack.push(treeRoot)
            visitTerminalRule(rule)
            return treeRoot
        }


        private fun getCurrentCardinality(): Cardinality {
            val res = cardinality
            cardinality = Cardinality.NONE
            return res
        }


        //========================================================
        //           Visitor methods
        //========================================================

        override fun visitTerminalAlternatives(alternatives: XtextTerminalAlternatives) {
            val terminalGroupList = mutableListOf(alternatives.terminalGroup)
            alternatives.terminalAlternativesSuffix1?.let { terminalGroupList.addAll(it.terminalGroupList) }
            val manyGroups = terminalGroupList.size > 1
            if (manyGroups) {
                val treeBranch = TreeTerminalBranch(treeNodeStack.peek())
                treeNodeStack.peek().addChild(treeBranch)
                treeNodeStack.push(treeBranch)
            }
            terminalGroupList.forEach {
                visitTerminalGroup(it)
            }
            if (manyGroups) treeNodeStack.pop()
        }

        override fun visitTerminalGroup(group: XtextTerminalGroup) {
            val terminalTokenList = mutableListOf(group.terminalToken)
            group.terminalGroupSuffix1?.let { terminalTokenList.addAll(it.terminalTokenList) }
            val manyTokens = terminalTokenList.size > 1
            if (manyTokens) {
                val treeGroup = TreeTerminalGroup(treeNodeStack.peek(), Cardinality.NONE, false)
                treeNodeStack.peek().addChild(treeGroup)
                treeNodeStack.push(treeGroup)
            }
            terminalTokenList.forEach {
                visitTerminalToken(it)
            }
            if (manyTokens) treeNodeStack.pop()
        }

        override fun visitTerminalToken(token: XtextTerminalToken) {
            token.plusKeyword?.let {
                cardinality = Cardinality.PLUS
            }
            token.quesMarkKeyword?.let {
                cardinality = Cardinality.QUES
            }
            token.asteriskKeyword?.let {
                cardinality = Cardinality.ASTERISK
            }
            visitTerminalTokenElement(token.terminalTokenElement)
        }

        override fun visitCharacterRange(characterRange: XtextCharacterRange) {
            val terminalRange = TreeTerminalRange(characterRange, treeNodeStack.peek(), getCurrentCardinality())
            treeNodeStack.peek().addChild(terminalRange)
        }

        override fun visitParenthesizedTerminalElement(parenthesizedTerminalElement: XtextParenthesizedTerminalElement) {
            val terminalGroup = TreeTerminalGroup(treeNodeStack.peek(), getCurrentCardinality(), true)
            treeNodeStack.peek().addChild(terminalGroup)
            treeNodeStack.push(terminalGroup)
            visitTerminalAlternatives(parenthesizedTerminalElement.terminalAlternatives)
            treeNodeStack.pop()

        }

        override fun visitWildcard(wildcard: XtextWildcard) {
            val terminalWildcard = TreeTerminalWildcard(wildcard, treeNodeStack.peek(), getCurrentCardinality())
            treeNodeStack.peek().addChild(terminalWildcard)
        }

        override fun visitUntilToken(untilToken: XtextUntilToken) {
            val terminalUntilToken = TreeTerminalUntil(untilToken, treeNodeStack.peek(), Cardinality.NONE)
            treeNodeStack.peek().addChild(terminalUntilToken)
        }

        override fun visitTerminalRuleCall(ruleCall: XtextTerminalRuleCall) {
            val terminalRuleCall = TreeTerminalRuleCall(ruleCall, treeNodeStack.peek(), getCurrentCardinality())
            treeNodeStack.peek().addChild(terminalRuleCall)
        }

        override fun visitNegatedToken(negatedToken: XtextNegatedToken) {
            val terminalNegatedToken = TreeTerminalNegatedToken(negatedToken, treeNodeStack.peek(), getCurrentCardinality())
            treeNodeStack.peek().addChild(terminalNegatedToken)
        }

    }


    private class XtextParserRuleVisitor(private val keywords: List<Keyword>, private val emfRegistry: EmfModelRegistry) : XtextVisitor() {
        private var lastAction: String? = null
        private var currentRule: TreeRule? = null
        private val treeNodeStack = Stack<TreeNodeImpl>()
        private val addedSuffixesInfos = Stack<SuffixInsertionInfo>()
        private var l = 0
        private var suffixCounter = 1
        private var newType = ""
        private val suffixes = mutableListOf<TreeRule>()
        private var cardinality: Cardinality = Cardinality.NONE



        fun createTreeFromXtextEnumRule(rule: XtextEnumRule): TreeRule {
            clearAll()
            val type = getRuleTypeDescriptor(rule)
            val treeRoot = TreeEnumRuleImpl(rule, type)
            currentRule = treeRoot
            treeNodeStack.push(treeRoot)
            visitEnumLiterals(rule.enumLiterals)
            return treeRoot
        }

        fun createTreesFromXtextParserRule(rule: XtextParserRule): List<TreeRule> {
            clearAll()
            val result = mutableListOf<TreeRule>()
            val treeRule: TreeRule =
                    rule.fragmentKeyword?.let {
                        TreeFragmentRuleImpl(rule)
                    } ?: kotlin.run {
                        val type = getRuleTypeDescriptor(rule)
                        TreeParserRuleImpl(rule, type)
                    }
            currentRule = treeRule
            treeNodeStack.push(treeRule as TreeNodeImpl)
            visitAlternatives(rule.alternatives)
            if (newType.isNotEmpty()) {
                assert(treeRule is TreeParserRule)
                emfRegistry.findOrCreateType(newType)?.let {
                    (treeRule as TreeParserRuleImpl).setReturnType(it)
                }
            }
            result.add(treeRule)
            result.addAll(suffixes)
            return result
        }

        private fun clearAll() {
            treeNodeStack.clear()
            lastAction = null
            suffixCounter = 1
            l = 0
            newType = ""
            suffixes.clear()
            currentRule = null
            cardinality = Cardinality.NONE
        }

        private fun getRuleTypeDescriptor(rule: XtextParserRule): EmfClassDescriptor {
            val returnTypeText = rule.typeRef?.text ?: rule.validID.text.eliminateCaret()
            val ruleType = emfRegistry.findOrCreateType(returnTypeText)
            if (ruleType == null) return EmfClassDescriptor.STRING
            return ruleType
        }

        private fun getRuleTypeDescriptor(rule: XtextEnumRule): EmfClassDescriptor {
            val returnTypeText = rule.typeRef?.text ?: rule.validID.text.eliminateCaret()
            val ruleType = emfRegistry.findOrCreateType(returnTypeText)
            if (ruleType == null) return EmfClassDescriptor.STRING
            return ruleType
        }

        private fun createTreeKeyword(psiElement: PsiElement, assignmentString: String?): TreeKeywordImpl {
            val psiElementText = psiElement.text
                    .removePrefix("\"").removePrefix("\'")
                    .removeSuffix("\"").removeSuffix("\'")
            val keywordName = keywords.firstOrNull { it.keyword == psiElementText }?.name
            assertNotNull(keywordName)
            val assignment = assignmentString?.let { Assignment.fromString(it) }
            return TreeKeywordImpl(psiElement, treeNodeStack.peek(), getCurrentCardinality(), keywordName, assignment)
        }

        private fun addTreeNode(node: TreeNodeImpl) {
            if (node is TreeLeaf && lastAction != null) {
                setActionToTreeLeaf(node as TreeLeafImpl, lastAction!!)
                lastAction = null
            }
            treeNodeStack.peek().addChild(node)
            if (node !is TreeLeaf) {
                treeNodeStack.add(node)
            }
        }


        private fun hasCardinality(token: XtextAbstractTokenWithCardinality): Boolean {
            return token.quesMarkKeyword != null || token.asteriskKeyword != null || token.plusKeyword != null
        }

        //look ahead and check if token is NOT optional
        private fun goodElement(token: XtextAbstractTokenWithCardinality): Boolean {
            if (hasCardinality(token)) return false

            //if token itself is not optional, check if it is parenthesized,
            // if so check optionality of every first token in group branches
            token.abstractTerminal?.parenthesizedElement?.let { parenthesizedElement ->
                val conditionalBranchList = mutableListOf(parenthesizedElement.alternatives.conditionalBranch)
                parenthesizedElement.alternatives.alternativesSuffix1?.let { conditionalBranchList.addAll(it.conditionalBranchList) }
                conditionalBranchList.forEach { branch ->
                    val firstTokenInBrackets =
                        PsiTreeUtil.getChildrenOfType(branch, XtextAbstractTokenWithCardinality::class.java)?.get(0)
                    firstTokenInBrackets?.let {
                        if (!goodElement(it)) return false
                    } ?: return false
                }
            }
            return true
        }

        private fun setActionToTreeLeaf(node: TreeLeafImpl, actionText: String) {
            val rewrite = createRewriteFromString(actionText)
            rewrite?.let {
                node.setRewrite(it)
            } ?: kotlin.run {
                val simpleActionText = actionText.removePrefix("{").removeSuffix("}")
                val action = emfRegistry.findOrCreateType(simpleActionText)
                action?.let {
                    node.setSimpleAction(it)
                }
            }
        }

        private fun insertSuffix() {
            val suffixName = "${currentRule!!.name}Suffix${suffixCounter++}"
            val suffixRule = TreeParserRuleImpl(suffixName, (currentRule as TreeParserRule).returnType, true)

            val peek = treeNodeStack.peek()
            if (peek is TreeRule && peek.children.isEmpty()) {
                assert(lastAction!!.split(".").size < 2)
                newType = lastAction!!.removePrefix("{").removeSuffix("}")
            } else if (peek is TreeGroup && peek.children.isEmpty()) {
                treeNodeStack.pop()
                treeNodeStack.peek().removeChild(peek)
                val psiRuleCall = XtextElementFactory.createAbstractTokenWithCardinality("$suffixName ${peek.cardinality}").abstractTerminal?.ruleCall
                assertNotNull(psiRuleCall)
                val ruleCallNode = TreeRuleCallImpl(psiRuleCall, treeNodeStack.peek(), peek.cardinality)
                addTreeNode(ruleCallNode)
                treeNodeStack.add(suffixRule)
                addedSuffixesInfos.add(SuffixInsertionInfo(l, true))
            } else {
                val psiRuleCall = XtextElementFactory.createRuleCall(suffixName)
                val ruleCallNode = TreeRuleCallImpl(psiRuleCall, treeNodeStack.peek(), Cardinality.NONE)
                addTreeNode(ruleCallNode)
                treeNodeStack.add(suffixRule)
                addedSuffixesInfos.add(SuffixInsertionInfo(l, false))
            }
            lastAction = null
        }


        private fun createRewriteFromString(string: String): TreeRewrite? {
            if (string.split(".").size < 2) return null
            val typeText = string.split(".")[0].removePrefix("{")
            val typeDescriptor = emfRegistry.findOrCreateType(typeText)
            assertNotNull(typeDescriptor)
            val textFragmentForAssignment = string.split(".")[1].removeSuffix("current}")
            return TreeRewrite(typeDescriptor, Assignment.fromString(textFragmentForAssignment))
        }

        private fun setCardinality(token: XtextAbstractTokenWithCardinality) {
            token.asteriskKeyword?.let { cardinality = Cardinality.ASTERISK }
            token.plusKeyword?.let { cardinality = Cardinality.PLUS }
            token.quesMarkKeyword?.let { cardinality = Cardinality.QUES }
        }

        private fun getCurrentCardinality(): Cardinality {
            val res = cardinality
            cardinality = Cardinality.NONE
            return res
        }

        private fun popSuffixIfNeeded() {
            if (addedSuffixesInfos.isEmpty()) return
            if (l == addedSuffixesInfos.peek().level) {
                assert(treeNodeStack.peek() is TreeRule)
                if (addedSuffixesInfos.peek().groupReplaced) {
                    suffixes.add(treeNodeStack.peek() as TreeRule)
                } else {
                    suffixes.add(treeNodeStack.pop() as TreeRule)
                }
                addedSuffixesInfos.pop()
            }
        }

        //===================================================
        //                   Visitor methods

        override fun visitAbstractTokenWithCardinality(tokenWithCardinality: XtextAbstractTokenWithCardinality) {
            setCardinality(tokenWithCardinality)

            if (lastAction != null && !goodElement(tokenWithCardinality)) {
                insertSuffix()
            }

            tokenWithCardinality.abstractTerminal?.let {
                visitAbstractTerminal(it)
            }
            tokenWithCardinality.assignment?.let {
                visitAssignment(it)
            }
        }


        override fun visitAbstractTerminal(abstractTerminal: XtextAbstractTerminal) {
            abstractTerminal.keyword?.let {
                visitKeyword(it)
            }
            abstractTerminal.ruleCall?.let {
                visitRuleCall(it)
            }
            abstractTerminal.parenthesizedElement?.let {
                val treeGroup = TreeGroupImpl(it, treeNodeStack.peek(), getCurrentCardinality())
                treeNodeStack.peek().addChild(treeGroup)
                treeNodeStack.push(treeGroup)
                l++
                visitParenthesizedElement(it)
                popSuffixIfNeeded()
                l--
                treeNodeStack.pop()
            }
            abstractTerminal.predicatedKeyword?.let {
                visitPredicatedKeyword(it)
            }
            abstractTerminal.predicatedRuleCall?.let {
                visitPredicatedRuleCall(it)
            }
            abstractTerminal.predicatedGroup?.let {
                visitPredicatedGroup(it)
            }
        }

        override fun visitAssignment(assignment: XtextAssignment) {
            var assignmentString = assignment.validID.text.replace("^", "")
            assignment.equalsKeyword?.let { assignmentString = "$assignmentString=" }
            assignment.plusEqualsKeyword?.let { assignmentString = "$assignmentString+=" }
            assignment.quesEqualsKeyword?.let { assignmentString = "$assignmentString?=" }

            visitAssignableTerminal(assignment.assignableTerminal, assignmentString)
        }

        override fun visitAlternatives(alternatives: XtextAlternatives) {
            val lastActionOnEntry = lastAction
            val conditionalBranchList = mutableListOf(alternatives.conditionalBranch)
            alternatives.alternativesSuffix1?.let { conditionalBranchList.addAll(it.conditionalBranchList) }
            var moreThanOneChild = conditionalBranchList.size > 1
            if (moreThanOneChild) {
                val treeBranch = TreeBranchImpl(treeNodeStack.peek())
                treeNodeStack.peek().addChild(treeBranch)
                treeNodeStack.push(treeBranch)
            }
            conditionalBranchList.forEach {
                if (lastActionOnEntry != null && lastAction == null) lastAction = lastActionOnEntry
                visitConditionalBranch(it)
                popSuffixIfNeeded()
            }
            if (moreThanOneChild) {
                treeNodeStack.pop()
            }
        }

        override fun visitConditionalBranch(branch: XtextConditionalBranch) {
            branch.unorderedGroup?.let { unorderedGroup ->
                val groupList = mutableListOf(unorderedGroup.group)
                unorderedGroup.unorderedGroupSuffix1?.let { groupList.addAll(it.groupList) }
                val tokensListSize = groupList
                    .flatMap {
                        val abstractTokenList = mutableListOf(it.abstractToken)
                        it.groupSuffix1?.let { abstractTokenList.addAll(it.abstractTokenList) }
                        abstractTokenList
                    }
                    .filter { it.abstractTokenWithCardinality != null }.size
                if (treeNodeStack.peek() is TreeGroup || treeNodeStack.peek() is TreeRule || tokensListSize < 2) {
                    visitUnorderedGroup(unorderedGroup)
                } else {
                    val treeGroup = TreeSyntheticGroup(treeNodeStack.peek(), getCurrentCardinality(), false)
                    treeNodeStack.peek().addChild(treeGroup)
                    treeNodeStack.push(treeGroup)
                    visitUnorderedGroup(unorderedGroup)
                    treeNodeStack.pop()
                }
            }
            //TODO branch with <Disjunction>
        }

        fun visitAssignableAlternatives(xtextAssignableAlternatives: XtextAssignableAlternatives, assignmentString: String) {
            val lastActionOnEntry = lastAction
            val assignableTerminalList = mutableListOf(xtextAssignableAlternatives.assignableTerminal)
            xtextAssignableAlternatives.assignableAlternativesSuffix1?.let { assignableTerminalList.addAll(it.assignableTerminalList) }
            var moreThanOneChild = assignableTerminalList.size > 1
            if (moreThanOneChild) {
                val treeBranch = TreeBranchImpl(treeNodeStack.peek())
                treeNodeStack.peek().addChild(treeBranch)
                treeNodeStack.push(treeBranch)
            }
            assignableTerminalList.forEach {
                if (lastActionOnEntry != null && lastAction == null) lastAction = lastActionOnEntry
                visitAssignableTerminal(it, assignmentString)
                popSuffixIfNeeded()
            }
            if (moreThanOneChild) {
                treeNodeStack.pop()
            }
        }

        private fun visitAssignableTerminal(assignableTerminal: XtextAssignableTerminal, assignmentString: String) {
            assignableTerminal.keyword?.let {
                val keywordNode = createTreeKeyword(it, assignmentString)
                addTreeNode(keywordNode)
            }
            assignableTerminal.ruleCall?.let {
                val treeLeafRuleCall = TreeRuleCallImpl(it, treeNodeStack.peek(), getCurrentCardinality(), Assignment.fromString(assignmentString))
                addTreeNode(treeLeafRuleCall)
            }
            assignableTerminal.parenthesizedAssignableElement?.let {
                val treeGroup = TreeSyntheticGroup(treeNodeStack.peek(), getCurrentCardinality(), true)
                treeNodeStack.peek().addChild(treeGroup)
                treeNodeStack.push(treeGroup)
                l++
                visitParenthesizedAssignableElement(it, assignmentString)
                popSuffixIfNeeded()
                l--
                treeNodeStack.pop()
            }
            assignableTerminal.crossReference?.let {
                val referenceType = emfRegistry.findOrCreateType(it.typeRef.text)
                assertNotNull(referenceType)
                val referenceNode = TreeCrossReferenceImpl(
                    it,
                    currentRule!!.name,
                    treeNodeStack.peek(),
                    getCurrentCardinality(),
                    referenceType,
                    Assignment.fromString(assignmentString)
                )
                addTreeNode(referenceNode)
            }
        }


        override fun visitUnorderedGroup(unorderedGroup: XtextUnorderedGroup) {
            val groupList = mutableListOf(unorderedGroup.group)
            unorderedGroup.unorderedGroupSuffix1?.let { groupList.addAll(it.groupList) }
            if (groupList.size == 1) {
                visitGroup(groupList.first())
            } else {
                throw Exception("Plugin does not support unordered groups")
            }
        }

        override fun visitKeyword(xtextKeyword: XtextKeyword) {
            val keywordNode = createTreeKeyword(xtextKeyword, null)
            addTreeNode(keywordNode)
        }


        fun visitParenthesizedAssignableElement(o: XtextParenthesizedAssignableElement, assignmentString: String) {
            visitAssignableAlternatives(o.assignableAlternatives, assignmentString)
        }

        override fun visitParenthesizedElement(parenthesizedElement: XtextParenthesizedElement) {
            visitAlternatives(parenthesizedElement.alternatives)
        }

        override fun visitPredicatedGroup(predicatedGroup: XtextPredicatedGroup) {
            visitAlternatives(predicatedGroup.alternatives)
        }

        override fun visitPredicatedKeyword(predicatedKeyword: XtextPredicatedKeyword) {
            val keywordNode = createTreeKeyword(predicatedKeyword.string, null)
            addTreeNode(keywordNode)
        }

        override fun visitRuleCall(ruleCall: XtextRuleCall) {
            val treeLeaf = TreeRuleCallImpl(ruleCall, treeNodeStack.peek(), getCurrentCardinality())
            addTreeNode(treeLeaf)
        }

        override fun visitPredicatedRuleCall(predicatedRuleCall: XtextPredicatedRuleCall) {
            val treeLeafRuleCall = TreeRuleCallImpl(predicatedRuleCall, treeNodeStack.peek(), getCurrentCardinality())
            addTreeNode(treeLeafRuleCall)
        }

        override fun visitAction(xtextAction: XtextAction) {
            lastAction = xtextAction.text
        }


        override fun visitEnumLiterals(enumLiterals: XtextEnumLiterals) {
            val enumLiteralDeclarationList = mutableListOf(enumLiterals.enumLiteralDeclaration)
            enumLiterals.enumLiteralsSuffix1?.let { enumLiteralDeclarationList.addAll(it.enumLiteralDeclarationList) }
            val branchAdded = enumLiteralDeclarationList.size > 1
            if (branchAdded) {
                val treeBranch = TreeBranchImpl(treeNodeStack.peek())
                addTreeNode(treeBranch)
            }
            enumLiteralDeclarationList.forEach {
                visitEnumLiteralDeclaration(it)
            }
            if (branchAdded) treeNodeStack.pop()
        }

        override fun visitEnumLiteralDeclaration(literalDeclaration: XtextEnumLiteralDeclaration) {
            val psiKeyword = literalDeclaration.keyword ?: literalDeclaration.referenceeEnumLiteralID
            val keywordNode = createTreeKeyword(psiKeyword, null)
            addTreeNode(keywordNode)
        }


        private data class SuffixInsertionInfo(val level: Int, val groupReplaced: Boolean)

    }
}
