package com.intellij.xtextLanguage.xtext.generator.models.elements.tree

interface TreeRoot : TreeNode {
    val name: String
    val isDatatypeRule: Boolean
    val isFragment: Boolean
    val superRuleName: String?

    fun hasNameFeature(): Boolean
}