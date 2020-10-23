package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeGroup
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextParenthesizedElement


class TreeGroupImpl(psiElement: XtextParenthesizedElement, parent: TreeNode, cardinality: Cardinality) : TreeNodeImpl(parent, cardinality), TreeGroup {
    override fun getString(): String {
        return _children.map { it.getString() }.joinToString(separator = " ", prefix = "(", postfix = ")") + cardinality.toString()
    }

}
