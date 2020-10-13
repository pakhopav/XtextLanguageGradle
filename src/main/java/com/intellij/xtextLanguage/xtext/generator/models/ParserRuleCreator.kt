package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.models.elements.Keyword
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.TreeRewrite
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeGroup
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRoot
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeSuffix
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl.*
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*
import java.util.*
import kotlin.test.assertNotNull

class ParserRuleCreator(keywords: List<Keyword>) {
    private val visitor = XtextParserRuleVisitor(keywords)

    fun createFromXtextParserRule(xtextRule: XtextParserRule): TreeRootImpl {
        return visitor.createRule(xtextRule)
    }

    fun createRule(ruleName: String, ruleBody: String, extension: String = "", fragment: Boolean = false, originRuleName: String? = null): TreeRootImpl {
        val ruleText = "${if (fragment) "fragment" else ""}$ruleName ${if (extension.isNotEmpty()) "returns $extension" else ""} : ${ruleBody};"
        val xtextRule = XtextElementFactory.createParserRule(ruleText)
        originRuleName?.let {
            return visitor.createDuplicateRule(xtextRule, it)
        }
        return createFromXtextParserRule(xtextRule)
    }


    private class XtextParserRuleVisitor(private val keywords: List<Keyword>) : XtextVisitor() {
        private var lastAction: String? = null
        private var currentRuleName = ""
        private val treeNodeStack = Stack<TreeNodeImpl>()
        private var suffixWasAdded = false
        private var suffixCounter = 1


        fun createRule(rule: XtextParserRule): TreeRootImpl {
            clearAll()
            val treeRoot = TreeRootImpl(rule)
            treeNodeStack.push(treeRoot)
            currentRuleName = rule.ruleNameAndParams.validID.text.replace("^", "").capitalize()
            visitAlternatives(rule.alternatives)
            return treeRoot
        }

        fun createDuplicateRule(rule: XtextParserRule, originRuleName: String): TreeRootImpl {
            clearAll()
            val treeRoot = DuplicateRuleImpl(rule, originRuleName)
            treeNodeStack.push(treeRoot)
            currentRuleName = rule.ruleNameAndParams.validID.text.replace("^", "").capitalize()
            visitAlternatives(rule.alternatives)
            return treeRoot
        }

        private fun clearAll() {
            treeNodeStack.clear()
            lastAction = null
            currentRuleName = ""
            suffixCounter = 1
        }

        private fun createTreeKeyword(psiElement: PsiElement, assignmentString: String?): TreeKeywordImpl {
            val psiElementText = psiElement.text.slice(1 until psiElement.text.length - 1)
            val keywordName = keywords.firstOrNull { it.keyword == psiElementText }?.name
            assertNotNull(keywordName)
            val assignment = assignmentString?.let { Assignment.fromString(it) }
            return TreeKeywordImpl(psiElement, treeNodeStack.peek(), keywordName, assignment)
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
            if (node is TreeSuffix) {
                treeNodeStack.add(node)
                suffixWasAdded = true
            }
        }


        private fun hasCardinality(token: XtextAbstractTokenWithCardinality): Boolean {
            return token.quesMarkKeyword != null || token.asteriskKeyword != null || token.plusKeyword != null
        }

        private fun goodElement(token: XtextAbstractTokenWithCardinality): Boolean {
            if (hasCardinality(token)) return false
            token.abstractTerminal?.parenthesizedElement?.let { parenthesizedElement ->
                parenthesizedElement.alternatives.conditionalBranchList.forEach { branch ->
                    val firstTokenInBrackets = PsiTreeUtil.getChildrenOfType(branch, XtextAbstractTokenWithCardinality::class.java)?.get(0)
                    firstTokenInBrackets?.let {
                        if (!goodElement(it)) return false
                    } ?: return false
                }
            }
            return true
        }

        override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
            if (lastAction != null && !goodElement(o)) {
                val suffixName = "${currentRuleName}Suffix${suffixCounter++}"
                //to change
                //Case if action is right under root: RULE : {A} (...)?
                if (noAssignmentsPrecededInBranch(o) && treeNodeStack.peek() !is TreeRoot) {
                    val currentPeek = treeNodeStack.peek()
                    val parentGroup = PsiTreeUtil.getParentOfType(o, XtextGroup::class.java)
                    val suffix = TreeSuffixImpl(parentGroup!!, suffixName, currentPeek.parent!!)
                    currentPeek.children.forEach { suffix.addChild(it as TreeNodeImpl) }
                    setActionToTreeLeaf(suffix, lastAction!!)
                    treeNodeStack.pop()
                    treeNodeStack.peek().replaceChild(currentPeek, suffix)
                    treeNodeStack.push(suffix)
                } else {
                    //to change
                    val parentGroup = PsiTreeUtil.getParentOfType(o, XtextGroup::class.java)
                    val suffix = TreeSuffixImpl(parentGroup!!, suffixName, treeNodeStack.peek())
                    addTreeNode(suffix)
                }
                lastAction = null
            }

            o.abstractTerminal?.let {
                visitAbstractTerminal(it)
            }
            o.assignment?.let {
                visitAssignment(it)
            }
        }

        private fun setActionToTreeLeaf(node: TreeLeafImpl, actionText: String) {
            val rewrite = createRewriteFromString(actionText)
            rewrite?.let {
                node.setRewrite(it)
            } ?: kotlin.run {
                val simpleActionText = actionText.removePrefix("{").removeSuffix("}")
                node.setSimpleAction(simpleActionText)
            }
        }


        private fun noAssignmentsPrecededInBranch(token: XtextAbstractTokenWithCardinality): Boolean {
            PsiTreeUtil.getParentOfType(token, XtextUnorderedGroup::class.java)?.let {
                val allTokensWithCardinalityInBranch = it.groupList
                        .flatMap { it.abstractTokenList }
                        .filter { it.abstractTokenWithCardinality != null }
                        .map { it.abstractTokenWithCardinality }
                allTokensWithCardinalityInBranch.forEach {
                    if (it == token) return true
                    PsiTreeUtil.findChildOfType(it, XtextAssignment::class.java)?.let {
                        return false
                    }
                }
            }
            return true
        }

        override fun visitAbstractTerminal(o: XtextAbstractTerminal) {
            o.keyword?.let {
                visitKeyword(it)
            }
            o.ruleCall?.let {
                visitRuleCall(it)
            }
            o.parenthesizedElement?.let {
                val treeGroup = TreeGroupImpl(it, treeNodeStack.peek())
                treeNodeStack.peek().addChild(treeGroup)
                treeNodeStack.push(treeGroup)
                visitParenthesizedElement(it)
                popSuffixIfNeed()
                treeNodeStack.pop()
            }
            o.predicatedKeyword?.let {
                visitPredicatedKeyword(it)
            }
            o.predicatedRuleCall?.let {
                visitPredicatedRuleCall(it)
            }
            o.predicatedGroup?.let {
                visitPredicatedGroup(it)
            }
        }

        override fun visitAssignment(o: XtextAssignment) {
            var assignmentString = o.validID.text.replace("^", "")
            o.equalsKeyword?.let { assignmentString = "$assignmentString=" }
            o.plusEqualsKeyword?.let { assignmentString = "$assignmentString+=" }
            o.quesEqualsKeyword?.let { assignmentString = "$assignmentString?=" }

            visitAssignableTerminal(o.assignableTerminal, assignmentString)
        }

        override fun visitAlternatives(alternatives: XtextAlternatives) {
            val lastActionOnEntry = lastAction
            var moreThanOneChild = alternatives.conditionalBranchList.size > 1
            if (moreThanOneChild) {
                val treeBranch = TreeBranchImpl(alternatives, treeNodeStack.peek())
                treeNodeStack.peek().addChild(treeBranch)
                treeNodeStack.push(treeBranch)
            }
            alternatives.conditionalBranchList.forEach {
                if (lastActionOnEntry != null && lastAction == null) lastAction = lastActionOnEntry
                visitConditionalBranch(it)
                popSuffixIfNeed()
            }
            if (moreThanOneChild) {
                treeNodeStack.pop()
            }
        }

        override fun visitConditionalBranch(o: XtextConditionalBranch) {
            o.unorderedGroup?.let { unorderedGroup ->
                val tokensListSize = unorderedGroup.groupList.flatMap { it.abstractTokenList }.filter { it.abstractTokenWithCardinality != null }.size
                if (treeNodeStack.peek() is TreeGroup || treeNodeStack.peek() is TreeRoot || tokensListSize < 2) {
                    visitUnorderedGroup(unorderedGroup)
                } else {
                    val treeGroup = TreeGroupImpl2(unorderedGroup, treeNodeStack.peek())
                    treeNodeStack.peek().addChild(treeGroup)
                    treeNodeStack.push(treeGroup)
                    visitUnorderedGroup(unorderedGroup)
                    treeNodeStack.pop()
                }
            }
            o.ruleFromConditionalBranchBranch2?.let {
                visitRuleFromConditionalBranchBranch2(it)
            }
        }

        fun visitAssignableAlternatives(xtextAssignableAlternatives: XtextAssignableAlternatives, assignmentString: String) {
            val lastActionOnEntry = lastAction
            var moreThanOneChild = xtextAssignableAlternatives.assignableTerminalList.size > 1
            if (moreThanOneChild) {
                val treeBranch = TreeBranchImpl1(xtextAssignableAlternatives, treeNodeStack.peek())
                treeNodeStack.peek().addChild(treeBranch)
                treeNodeStack.push(treeBranch)
            }
            xtextAssignableAlternatives.assignableTerminalList.forEach {
                if (lastActionOnEntry != null && lastAction == null) lastAction = lastActionOnEntry
                visitAssignableTerminal(it, assignmentString)
                popSuffixIfNeed()
            }
            if (moreThanOneChild) {
                treeNodeStack.pop()
            }
        }

        fun visitAssignableTerminal(assignableTerminal: XtextAssignableTerminal, assignmentString: String) {
            assignableTerminal.keyword?.let {
                val keywordNode = createTreeKeyword(it, assignmentString)
                addTreeNode(keywordNode)
            }
            assignableTerminal.ruleCall?.let {
                val treeLeafRuleCall = TreeRuleCallImpl(it, treeNodeStack.peek(), Assignment.fromString(assignmentString))
                addTreeNode(treeLeafRuleCall)
            }
            assignableTerminal.parenthesizedAssignableElement?.let {
                val treeGroup = TreeGroupImpl1(it, treeNodeStack.peek())
                treeNodeStack.peek().addChild(treeGroup)
                treeNodeStack.push(treeGroup)
                visitParenthesizedAssignableElement(it, assignmentString)
                popSuffixIfNeed()
                treeNodeStack.pop()
            }
            assignableTerminal.crossReference?.let {
                addTreeNode(TreeCrossReferenceImpl(it, currentRuleName, treeNodeStack.peek(), Assignment.fromString(assignmentString)))
            }
        }


        override fun visitUnorderedGroup(o: XtextUnorderedGroup) {
            if (o.groupList.size == 1) {
                visitGroup(o.groupList.first())
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

        override fun visitParenthesizedElement(o: XtextParenthesizedElement) {
            visitAlternatives(o.alternatives)
        }

        override fun visitPredicatedGroup(o: XtextPredicatedGroup) {
            visitAlternatives(o.alternatives)
        }

        override fun visitPredicatedKeyword(predicatedKeyword: XtextPredicatedKeyword) {
            val keywordNode = createTreeKeyword(predicatedKeyword, null)
            addTreeNode(keywordNode)
        }

        override fun visitRuleCall(o: XtextRuleCall) {
            val treeLeaf = TreeRuleCallImpl(o, treeNodeStack.peek())
            addTreeNode(treeLeaf)
        }

        override fun visitPredicatedRuleCall(o: XtextPredicatedRuleCall) {
            val treeLeafRuleCall = TreeRuleCallImpl(o, treeNodeStack.peek())
            addTreeNode(treeLeafRuleCall)
        }

        override fun visitAction(o: XtextAction) {
            lastAction = o.text
        }

        private fun createRewriteFromString(string: String): TreeRewrite? {
            if (string.split(".").size < 2) return null
            val className = string.split(".")[0].removePrefix("{")
            val textFragmentForAssignment = string.split(".")[1].removeSuffix("current}")
            return TreeRewrite(className, Assignment.fromString(textFragmentForAssignment))
        }

        private fun popSuffixIfNeed() {
            if (suffixWasAdded) {
                treeNodeStack.pop()
                suffixWasAdded = false
            }
        }

    }
}
