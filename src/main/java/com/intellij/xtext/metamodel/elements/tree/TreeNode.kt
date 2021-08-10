package com.intellij.xtext.metamodel.elements.tree

import com.intellij.xtext.metamodel.elements.Cardinality

interface TreeNode {
    val children: List<TreeNode>
    val cardinality: Cardinality

    fun getString(): String

    companion object {
        fun TreeNode.filterNodesInSubtree(condition: (node: TreeNode) -> Boolean): List<TreeNode> {
            val filteredNodes = mutableListOf<TreeNode>()
            if (this is TreeRuleCall && this.getCalledRule() is TreeFragmentRule) {
                this.getCalledRule()!!.children.forEach {
                    filteredNodes.addAll(it.filterNodesInSubtree(condition))
                }
            }
            this.children.forEach {
                filteredNodes.addAll(it.filterNodesInSubtree(condition))
            }
            if (condition(this)) filteredNodes.add(this)
            return filteredNodes
        }

        fun <T> TreeNode.filterNodesIsInstance(type: Class<T>): List<T> {
            return this.filterNodesInSubtree { type.isInstance(it) }.map { it as T }
        }

    }
}

fun String.eliminateCaret(): String {
    return this.replace("^", "")
}
