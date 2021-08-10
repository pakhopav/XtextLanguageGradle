package com.intellij.xtext.metamodel.elements.tree

import com.intellij.xtext.metamodel.model.elements.emf.Assignment
import com.intellij.xtext.metamodel.model.elements.emf.EmfClassDescriptor
import com.intellij.xtext.metamodel.model.elements.emf.TreeRewrite

interface TreeLeaf : TreeNode {
    val assignment: Assignment?
    val rewrite: TreeRewrite?
    val simpleAction: EmfClassDescriptor?

    fun getPsiElementTypeName(): String
    fun getBnfName(): String
}