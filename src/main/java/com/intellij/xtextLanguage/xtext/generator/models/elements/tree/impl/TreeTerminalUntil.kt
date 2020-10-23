package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextUntilToken

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