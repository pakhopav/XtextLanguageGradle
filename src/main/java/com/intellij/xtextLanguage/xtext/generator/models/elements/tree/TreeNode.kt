package com.intellij.xtextLanguage.xtext.generator.models.elements.tree

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality

interface TreeNode {
    val children: List<TreeNode>
    val cardinality: Cardinality

    fun getBnfString(): String
    fun setSpecificBnfString(string: String)
    fun filterNodesInSubtree(condition: (node: TreeNode) -> Boolean): List<TreeNode>
}

fun String.eliminateCaret(): String {
    return this.replace("^", "")
}
