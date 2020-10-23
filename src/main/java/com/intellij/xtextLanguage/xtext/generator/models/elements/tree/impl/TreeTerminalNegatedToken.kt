package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextNegatedToken

class TreeTerminalNegatedToken(psiElement: XtextNegatedToken, parent: TreeNode, cardinality: Cardinality) : TreeNodeImpl(parent, cardinality) {


    init {
        psiElement.terminalTokenElement.characterRange?.let {
            val terminalRange = TreeTerminalRange(it, this, Cardinality.NONE)
            _children.add(terminalRange)
        }
        psiElement.terminalTokenElement.parenthesizedTerminalElement?.let {
            it.terminalAlternatives.terminalGroupList.forEach {
                if (it.terminalTokenList.size > 1) {
                    throw Exception("Wrong negated terminal")
                } else {
                    it.terminalTokenList.first().terminalTokenElement.characterRange?.let {
                        val terminalRange = TreeTerminalRange(it, this, Cardinality.NONE)
                        _children.add(terminalRange)
                    } ?: throw Exception("Wrong negated terminal")
                }
            }
        }
    }

    override fun getString(): String {
        return _children.map { it.getString() }.joinToString(prefix = "[^", postfix = "]", separator = "")
    }

}