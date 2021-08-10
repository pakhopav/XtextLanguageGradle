package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextWildcard
import com.intellij.xtext.metamodel.elements.Cardinality
import com.intellij.xtext.metamodel.elements.tree.TreeNode

class TreeTerminalWildcard(private val psiElement: XtextWildcard, parent: TreeNode, cardinality: Cardinality) :
    TreeNodeImpl(parent, cardinality) {
    override fun getString(): String {
        return psiElement.text
    }
}