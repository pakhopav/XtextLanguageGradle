package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextUntilToken
import com.intellij.xtext.metamodel.model.elements.Cardinality
import com.intellij.xtext.metamodel.model.elements.tree.TreeNode

class TreeTerminalUntil(psiElement: XtextUntilToken, parent: TreeNode, cardinality: Cardinality) : TreeNodeImpl(parent, cardinality) {

    init {
        psiElement.terminalTokenElement.characterRange?.let {
            if (it.keywordList.size == 1) {
                val treeKeyword = TreeTerminalKeyword(it.keywordList.first(), this, Cardinality.NONE)
                _children.add(treeKeyword)
            }
        }
    }

    override fun getString(): String {
        _children.firstOrNull()?.let {
            return "([^\"*\"]*(\"*\"+[^\"*\"\"/\"])?)*(\"*\"+\"/\")?${it.getString()}"
        }
        return ""
    }

}