package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.models.BridgeRuleTypeRegistry
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.TreeRewrite
import com.intellij.xtextLanguage.xtext.psi.*


interface TreeRoot : TreeNode {
}

interface TreeNode {
    val children: List<TreeNode>
    val cardinality: Cardinality
    val rewrite: TreeRewrite?
    val assignment: Assignment?
}

interface TreeBranch : TreeNode {

}

interface TreeGroup : TreeNode {

}

interface TreeLeaf : TreeNode {
    val ruleElement: RuleElement
}


abstract class TreeNodeImpl(protected val psiElement: PsiElement, val parent: TreeNode?) : TreeNode {
    protected val _children = mutableListOf<TreeNode>()
    override val children: List<TreeNode>
        get() = _children

    fun addChild(child: TreeNodeImpl) {
        _children.add(child)
    }

    protected fun getCardinalityOfPsiElement(): Cardinality {
        val psiParentWithCardinality = PsiTreeUtil.getParentOfType(psiElement, XtextAbstractTokenWithCardinality::class.java)
        psiParentWithCardinality?.let {
            psiParentWithCardinality.asteriskKeyword?.let { return Cardinality.ASTERISK }
            psiParentWithCardinality.quesMarkKeyword?.let { return Cardinality.QUES }
            psiParentWithCardinality.plusKeyword?.let { return Cardinality.PLUS }
        }
        return Cardinality.NONE
    }

    protected fun createRewriteFromString(string: String): TreeRewrite {
        var className = string.split(".")[0]
        className = className.removePrefix("{")
        var textFragmentForAssignment = string.split(".")[1].removeSuffix("current}")
        return TreeRewrite(getEmfRegistry().findOrCreateType(className), Assignment.fromString(textFragmentForAssignment))
    }

    protected fun getEmfRegistry(): BridgeRuleTypeRegistry {
        if (this is TreeRootImpl) return this.registry
        return (parent as TreeNodeImpl).getEmfRegistry()
    }

}

class TreeRootImpl(psiRule: XtextParserRule, val registry: BridgeRuleTypeRegistry) : TreeNodeImpl(psiRule, null), TreeRoot {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val rewrite = null
    override val assignment = null
}

class TreeBranchImpl(psiAlternatives: XtextAlternatives, parent: TreeNode) : TreeNodeImpl(psiAlternatives, parent), TreeBranch {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val rewrite = null
    override val assignment = null
}

class TreeBranchImpl1(psiAlternatives: XtextAssignableAlternatives, parent: TreeNode) : TreeNodeImpl(psiAlternatives, parent), TreeBranch {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val rewrite = null
    override val assignment = null
}

class TreeGroupImpl(psiElement: XtextConditionalBranch, parent: TreeNode, actionText: String? = null, assignment: Assignment? = null) : TreeNodeImpl(psiElement, parent), TreeGroup {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val rewrite = actionText?.let { createRewriteFromString(it) }

    override val assignment = assignment

}

class TreeGroupImpl1(psiElement: XtextParenthesizedElement, parent: TreeNode, actionText: String? = null, assignment: Assignment? = null) : TreeNodeImpl(psiElement, parent), TreeGroup {
    override val cardinality = getCardinalityOfPsiElement()
    override val rewrite = actionText?.let { createRewriteFromString(it) }
    override val assignment = assignment
}

class TreeGroupImpl2(psiElement: XtextParenthesizedAssignableElement, parent: TreeNode, actionText: String? = null, assignment: Assignment? = null) : TreeNodeImpl(psiElement, parent), TreeGroup {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val rewrite = actionText?.let { createRewriteFromString(it) }
    override val assignment = assignment
}

class TreeLeafImpl(override val ruleElement: RuleElement, parent: TreeNode, actionText: String? = null, assignment: Assignment? = null) : TreeNodeImpl(ruleElement.psiElement, parent), TreeLeaf {
    override val cardinality: Cardinality
        get() = getCardinalityOfPsiElement()
    override val rewrite = actionText?.let { createRewriteFromString(it) }
    override val assignment = assignment
}
