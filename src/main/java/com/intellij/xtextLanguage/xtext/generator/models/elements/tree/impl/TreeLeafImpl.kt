package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.TreeRewrite
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractTokenWithCardinality

abstract class TreeLeafImpl(protected val psiElement: PsiElement, override val parent: TreeNode, assignment: Assignment? = null) : TreeNodeImpl(parent), TreeLeaf {
    override val assignment = assignment
    protected var _rewrite: TreeRewrite? = null
    override val rewrite: TreeRewrite?
        get() = _rewrite

    protected var _simpleActionText: String? = null
    override val simpleActionText: String?
        get() = _simpleActionText

    protected fun getCardinalityOfPsiElement(): Cardinality {
        val psiParentWithCardinality = PsiTreeUtil.getParentOfType(psiElement, XtextAbstractTokenWithCardinality::class.java)
        psiParentWithCardinality?.let {
            psiParentWithCardinality.asteriskKeyword?.let { return Cardinality.ASTERISK }
            psiParentWithCardinality.quesMarkKeyword?.let { return Cardinality.QUES }
            psiParentWithCardinality.plusKeyword?.let { return Cardinality.PLUS }
        }
        return Cardinality.NONE
    }

    fun setRewrite(rewrite: TreeRewrite) {
        _rewrite = rewrite
    }

    fun setSimpleAction(actionText: String) {
        _simpleActionText = actionText
    }
}