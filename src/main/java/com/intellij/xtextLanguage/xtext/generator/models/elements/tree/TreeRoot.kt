package com.intellij.xtextLanguage.xtext.generator.models.elements.tree

interface TreeRoot : TreeNode {
    val name: String
    val isDatatypeRule: Boolean

    fun hasNameFeature(): Boolean
}