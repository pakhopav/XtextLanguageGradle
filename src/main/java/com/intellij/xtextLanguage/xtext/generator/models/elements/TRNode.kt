package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.psi.*


interface MetaAction {

}

interface MetaSimpleAction : MetaAction

interface MetaAssignedAction : MetaAction

class MetaSimpleActionImpl(val psiAction: XtextAction) : MetaSimpleAction {

}

class MetaAssignedActionImpl(val psiAction: XtextAction) : MetaAssignedAction {

}

interface MetaAssignment {}

class MetaAssignmentImpl(val psiAssignment: XtextAssignment) : MetaAssignment {

}

interface TreeRoot : TreeNode {
}

interface TreeNode {
    val children: List<TreeNode>
    val cardinality: Cardinality
    val action: MetaAction?
    val assignment: MetaAssignment?
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
    override val action = null
    override val assignment = null
}

class TreeBranchImpl(psiAlternatives: XtextAlternatives) : TreeNodeImpl(psiAlternatives), TreeBranch {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val action = null
    override val assignment = null
}

class TreeBranchImpl1(psiAlternatives: XtextAssignableAlternatives) : TreeNodeImpl(psiAlternatives), TreeBranch {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val action = null
    override val assignment = null
}

class TreeGroupImpl(psiElement: XtextConditionalBranch, action: MetaAction? = null, assignment: MetaAssignment? = null) : TreeNodeImpl(psiElement), TreeGroup {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val action = action
    override val assignment = assignment

}

class TreeGroupImpl1(psiElement: XtextParenthesizedElement, action: MetaAction? = null, assignment: MetaAssignment? = null) : TreeNodeImpl(psiElement), TreeGroup {
    override val cardinality = getCardinalityOfPsiElement()
    override val action = action
    override val assignment = assignment
}

class TreeGroupImpl2(psiElement: XtextParenthesizedAssignableElement, action: MetaAction? = null, assignment: MetaAssignment? = null) : TreeNodeImpl(psiElement), TreeGroup {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val action = action
    override val assignment = assignment
}

class TreeLeafImpl(override val ruleElement: RuleElement, action: MetaAction? = null, assignment: MetaAssignment? = null) : TreeNodeImpl(ruleElement.psiElement), TreeLeaf {
    override val cardinality: Cardinality
        get() = getCardinalityOfPsiElement()
    override val action = action
    override val assignment = assignment
}
