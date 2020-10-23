package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

class TreeTerminalGroup(parent: TreeNode, cardinality: Cardinality, private val withBrackets: Boolean) : TreeNodeImpl(parent, cardinality) {
    override fun getString(): String {
        if (withBrackets) {
            _children.map { it.getString() }.joinToString(separator = "", prefix = "(", postfix = ")") + cardinality
        }
        return _children.map { it.getString() }.joinToString(separator = "") + cardinality
    }
}