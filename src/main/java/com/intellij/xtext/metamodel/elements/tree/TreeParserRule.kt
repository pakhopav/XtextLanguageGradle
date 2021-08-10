package com.intellij.xtext.metamodel.elements.tree


interface TreeParserRule : TreeRule {
    val superRuleName: String?
    val isReferenced: Boolean
    val isSuffix: Boolean

    fun hasNameFeature(): Boolean
}