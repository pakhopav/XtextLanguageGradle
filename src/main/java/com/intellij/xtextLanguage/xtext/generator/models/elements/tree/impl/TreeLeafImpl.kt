package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.TreeRewrite
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

abstract class TreeLeafImpl(protected val psiElement: PsiElement, parent: TreeNode, cardinality: Cardinality, assignment: Assignment? = null) : TreeNodeImpl(parent, cardinality), TreeLeaf {
    override val assignment = assignment
    protected var _rewrite: TreeRewrite? = null
    override val rewrite: TreeRewrite?
        get() = _rewrite

    protected var _simpleActionText: String? = null
    override val simpleActionText: String?
        get() = _simpleActionText



    fun setRewrite(rewrite: TreeRewrite) {
        _rewrite = rewrite
    }

    fun setSimpleAction(actionText: String) {
        _simpleActionText = actionText
    }
}