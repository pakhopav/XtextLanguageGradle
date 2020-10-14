package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

abstract class TreeNodeImpl(open val parent: TreeNode?) : TreeNode {
    protected val _children = mutableListOf<TreeNode>()
    override val children: List<TreeNode>
        get() = _children

    protected var specificString: String? = null

    fun addChild(child: TreeNodeImpl) {
        _children.add(child)
    }

    fun replaceChild(toReplace: TreeNode, newNode: TreeNode) {
        val index = _children.indexOf(toReplace)
        if (index != -1) {
            _children.remove(toReplace)
            _children.add(index, newNode)
        }
    }

    fun setSpecificBnfString(string: String) {
        specificString = string
    }

}