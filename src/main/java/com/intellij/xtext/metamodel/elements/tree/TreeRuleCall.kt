package com.intellij.xtext.metamodel.elements.tree

interface TreeRuleCall : TreeLeaf {
    fun getCalledRule(): TreeRule?
}