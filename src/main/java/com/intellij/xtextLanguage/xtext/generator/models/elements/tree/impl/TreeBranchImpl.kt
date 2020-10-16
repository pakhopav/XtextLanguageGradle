package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeBranch
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

class TreeBranchImpl(parent: TreeNode) : TreeNodeImpl(parent), TreeBranch {
    override val cardinality: Cardinality
        get() = Cardinality.NONE


    override fun getBnfString(): String {
        return _children.map { it.getBnfString() }.joinToString(separator = " | ")
    }
}