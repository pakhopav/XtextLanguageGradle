package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextNegatedToken
import com.intellij.xtextLanguage.xtext.psi.XtextParenthesizedTerminalElement
import kotlin.test.assertTrue

class TreeTerminalNegatedToken(psiElement: XtextNegatedToken, parent: TreeNode, cardinality: Cardinality) : TreeNodeImpl(parent, cardinality) {
    init {
        psiElement.terminalTokenElement.characterRange?.let {
            val terminalRange = TreeTerminalRange(it, this, Cardinality.NONE)
            _children.add(terminalRange)
        }
        psiElement.terminalTokenElement.parenthesizedTerminalElement?.let {
            if (isValidParenthesizedElement(it)) {
                it.terminalAlternatives.terminalGroupList
                        .flatMap { it.terminalTokenList }
                        .map { it.terminalTokenElement }
                        .forEach {
                            it.characterRange?.let {
                                val terminalRange = TreeTerminalRange(it, this, Cardinality.NONE)
                                _children.add(terminalRange)
                            }
                        }
            }
        }
    }

    override fun getString(): String {
        var string = _children.map { it.getString() }.joinToString(prefix = "[^", postfix = "]", separator = "")
        if (cardinality != Cardinality.NONE) {
            string = "($string)$cardinality"
        }
        return string
    }


    private fun isValidParenthesizedElement(element: XtextParenthesizedTerminalElement): Boolean {
        element.terminalAlternatives.terminalGroupList.flatMap { it.terminalTokenList }.forEach {
            if (it.asteriskKeyword == null && it.quesMarkKeyword == null && it.plusKeyword == null) {
                if (it.terminalTokenElement.characterRange == null) {
                    return false
                }
            } else {
                return false
            }
        }
        return true
    }
}

fun TreeTerminalNegatedToken.toRegex(): Regex {
    var tokenString = this.toString()
    if (!tokenString.endsWith("]")) {
        tokenString = tokenString.slice(0..tokenString.length - 2)
        assertTrue(tokenString.endsWith("]"))
    }
    return tokenString.toRegex()
}