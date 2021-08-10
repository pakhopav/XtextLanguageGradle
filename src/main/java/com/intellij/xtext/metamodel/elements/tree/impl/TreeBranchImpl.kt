package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.metamodel.model.elements.Cardinality
import com.intellij.xtext.metamodel.model.elements.tree.TreeBranch
import com.intellij.xtext.metamodel.model.elements.tree.TreeNode

class TreeBranchImpl(parent: TreeNode) : TreeNodeImpl(parent, Cardinality.NONE), TreeBranch {


    override fun getString(): String {
        return _children.map { it.getString() }.joinToString(separator = " | ")
    }
}