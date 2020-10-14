package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeGroup
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

class TreeGroupImpl1(parent: TreeNode) : TreeNodeImpl(parent), TreeGroup {
    override val cardinality: Cardinality
        get() = Cardinality.NONE

    override fun getBnfString(): String {
        specificString?.let { return it }
        return _children.map { it.getBnfString() }.joinToString(separator = " ", prefix = "(", postfix = ")")
    }

}