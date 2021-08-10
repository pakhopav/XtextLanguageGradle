package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.metamodel.elements.Cardinality
import com.intellij.xtext.metamodel.elements.tree.TreeNode

class TreeTerminalBranch(parent: TreeNode) : TreeNodeImpl(parent, Cardinality.NONE) {
    override fun getString(): String {
        return _children.map { it.getString() }.joinToString(prefix = "(", postfix = ")", separator = "|")
    }
}