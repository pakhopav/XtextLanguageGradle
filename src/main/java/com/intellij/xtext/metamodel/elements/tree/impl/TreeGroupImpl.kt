package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextParenthesizedElement
import com.intellij.xtext.metamodel.elements.Cardinality
import com.intellij.xtext.metamodel.elements.tree.TreeGroup
import com.intellij.xtext.metamodel.elements.tree.TreeNode


class TreeGroupImpl(psiElement: XtextParenthesizedElement, parent: TreeNode, cardinality: Cardinality) :
    TreeNodeImpl(parent, cardinality), TreeGroup {
    override fun getString(): String {
        return _children.map { it.getString() }
            .joinToString(separator = " ", prefix = "(", postfix = ")") + cardinality.toString()
    }

}
