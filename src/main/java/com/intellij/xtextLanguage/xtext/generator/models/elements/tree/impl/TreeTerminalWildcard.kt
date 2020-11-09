package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextWildcard

class TreeTerminalWildcard(private val psiElement: XtextWildcard, parent: TreeNode, cardinality: Cardinality) : TreeNodeImpl(parent, cardinality) {
    override fun getString(): String {
        return psiElement.text
    }
}