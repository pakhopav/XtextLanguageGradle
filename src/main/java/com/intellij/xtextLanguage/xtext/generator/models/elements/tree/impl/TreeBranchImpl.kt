package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeBranch
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

class TreeBranchImpl(parent: TreeNode) : TreeNodeImpl(parent, Cardinality.NONE), TreeBranch {


    override fun getString(): String {
        return _children.map { it.getString() }.joinToString(separator = " | ")
    }
}