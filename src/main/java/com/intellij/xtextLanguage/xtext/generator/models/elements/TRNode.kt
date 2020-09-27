package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.psi.*

interface TreeRoot : TreeNode {
}

interface TreeNode {
    val children: List<TreeNode>
    val cardinality: Cardinality
}

interface TreeBranch : TreeNode {

}

interface TreeGroup : TreeNode {

}

interface TreeLeaf : TreeNode {
    val ruleElement: RuleElement
}


abstract class TreeNodeImpl(protected val psiElement: PsiElement) : TreeNode {
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
}

class TreeRootImpl(psiRule: XtextParserRule) : TreeNodeImpl(psiRule), TreeRoot {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
}

class TreeBranchImpl(psiAlternatives: XtextAlternatives) : TreeNodeImpl(psiAlternatives), TreeBranch {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
}

class TreeBranchImpl1(psiAlternatives: XtextAssignableAlternatives) : TreeNodeImpl(psiAlternatives), TreeBranch {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
}

class TreeGroupImpl(psiElement: XtextConditionalBranch) : TreeNodeImpl(psiElement), TreeGroup {
    override val cardinality: Cardinality
        get() = Cardinality.NONE

}

class TreeGroupImpl1(psiElement: XtextParenthesizedElement) : TreeNodeImpl(psiElement), TreeGroup {
    override val cardinality = getCardinalityOfPsiElement()
}

class TreeGroupImpl2(psiElement: XtextParenthesizedAssignableElement) : TreeNodeImpl(psiElement), TreeGroup {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
}

class TreeLeafImpl(override val ruleElement: RuleElement) : TreeNodeImpl(ruleElement.psiElement), TreeLeaf {
    override val cardinality: Cardinality
        get() = getCardinalityOfPsiElement()
}
