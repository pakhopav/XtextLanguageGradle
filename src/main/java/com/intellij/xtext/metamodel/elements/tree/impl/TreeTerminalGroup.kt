package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.metamodel.elements.Cardinality
import com.intellij.xtext.metamodel.elements.tree.TreeNode

class TreeTerminalGroup(parent: TreeNode, cardinality: Cardinality, private val withBrackets: Boolean) : TreeNodeImpl(parent, cardinality) {
    override fun getString(): String {
        if (withBrackets) {
            return _children.map { it.getString() }.joinToString(separator = "", prefix = "(", postfix = ")") + cardinality
        }
        return _children.map { it.getString() }.joinToString(separator = "") + cardinality
    }
}