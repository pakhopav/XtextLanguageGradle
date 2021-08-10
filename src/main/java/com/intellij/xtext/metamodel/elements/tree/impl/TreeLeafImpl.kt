package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.xtext.metamodel.elements.Cardinality
import com.intellij.xtext.metamodel.elements.emf.Assignment
import com.intellij.xtext.metamodel.elements.emf.EmfClassDescriptor
import com.intellij.xtext.metamodel.elements.emf.TreeRewrite
import com.intellij.xtext.metamodel.elements.tree.TreeLeaf
import com.intellij.xtext.metamodel.elements.tree.TreeNode

abstract class TreeLeafImpl(protected val psiElement: PsiElement, parent: TreeNode, cardinality: Cardinality, assignment: Assignment? = null) : TreeNodeImpl(parent, cardinality), TreeLeaf {
    override val assignment = assignment
    protected var _rewrite: TreeRewrite? = null
    override val rewrite: TreeRewrite?
        get() = _rewrite

    protected var _simpleAction: EmfClassDescriptor? = null
    override val simpleAction: EmfClassDescriptor?
        get() = _simpleAction


    fun setRewrite(rewrite: TreeRewrite) {
        _rewrite = rewrite
    }

    fun setSimpleAction(action: EmfClassDescriptor) {
        _simpleAction = action
    }
}