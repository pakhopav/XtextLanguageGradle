package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

class TreeTerminalBranch(parent: TreeNode) : TreeNodeImpl(parent, Cardinality.NONE) {
    override fun getString(): String {
        return _children.map { it.getString() }.joinToString(prefix = "(", postfix = ")", separator = "|")
    }
}