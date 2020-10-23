package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

class TreeTerminalSimple(private val psiElement: PsiElement, parent: TreeNode, cardinality: Cardinality) : TreeNodeImpl(parent, cardinality) {
    override fun getString(): String {
        return psiElement.text
    }
}