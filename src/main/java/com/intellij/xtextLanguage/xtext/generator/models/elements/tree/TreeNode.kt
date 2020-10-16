package com.intellij.xtextLanguage.xtext.generator.models.elements.tree

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality

interface TreeNode {
    val children: List<TreeNode>
    val cardinality: Cardinality

    fun getBnfString(): String


    companion object {
        fun TreeNode.filterNodesInSubtree(condition: (node: TreeNode) -> Boolean): List<TreeNode> {
            val filteredNodes = mutableListOf<TreeNode>()
            if (this is TreeRuleCall) {
                this.getCalledFragmentRule()?.let {
                    it.children.forEach {
                        filteredNodes.addAll(it.filterNodesInSubtree(condition))
                    }
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
