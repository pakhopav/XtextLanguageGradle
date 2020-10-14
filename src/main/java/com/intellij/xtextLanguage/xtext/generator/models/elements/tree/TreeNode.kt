package com.intellij.xtextLanguage.xtext.generator.models.elements.tree

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality

interface TreeNode {
    val children: List<TreeNode>
    val cardinality: Cardinality

    fun getBnfString(): String

    companion object {
        fun TreeNode.filterNodesInSubtree(condition: (node: TreeNode) -> Boolean): List<TreeNode> {
            val filteredNodes = mutableListOf<TreeNode>()
            this.children.forEach {
                filteredNodes.addAll(it.filterNodesInSubtree(condition))
            }
            if (condition(this)) filteredNodes.add(this)
            return filteredNodes
        }
    }
}

fun String.eliminateCaret(): String {
    return this.replace("^", "")
}
