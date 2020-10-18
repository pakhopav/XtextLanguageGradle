package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeGroup
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

class TreeSyntheticGroup(parent: TreeNode, cardinality: Cardinality, private val withBrackets: Boolean) : TreeNodeImpl(parent, cardinality), TreeGroup {

    override fun getBnfString(): String {
        if (withBrackets) {
            return _children.map { it.getBnfString() }.joinToString(separator = " ", prefix = "(", postfix = ")") + cardinality.toString()
        } else {
            return _children.map { it.getBnfString() }.joinToString(separator = " ") + cardinality.toString()
        }
    }

}