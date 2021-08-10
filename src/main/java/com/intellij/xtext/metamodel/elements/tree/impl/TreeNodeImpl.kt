package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.metamodel.elements.Cardinality
import com.intellij.xtext.metamodel.elements.tree.TreeNode

abstract class TreeNodeImpl(open var parent: TreeNode?, override val cardinality: Cardinality) : TreeNode {

    protected open val _children = mutableListOf<TreeNode>()
    override val children: List<TreeNode>
        get() = _children


    open fun addChild(child: TreeNodeImpl) {
        _children.add(child)
    }

    open fun removeChild(child: TreeNodeImpl) {
        _children.remove(child)
    }

    open fun removeAll(nodes: List<TreeNodeImpl>) {
        _children.removeAll(nodes)
    }

    open fun replaceChild(toReplace: TreeNode, newNode: TreeNode) {
        val index = _children.indexOf(toReplace)
        if (index != -1) {
            _children.remove(toReplace)
            _children.add(index, newNode)
        }
    }

}