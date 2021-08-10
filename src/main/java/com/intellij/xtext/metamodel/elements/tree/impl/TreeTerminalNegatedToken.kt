package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtext.language.psi.XtextCharacterRange
import com.intellij.xtext.language.psi.XtextNegatedToken
import com.intellij.xtext.language.psi.XtextParenthesizedTerminalElement
import com.intellij.xtext.metamodel.model.elements.Cardinality
import com.intellij.xtext.metamodel.model.elements.tree.TreeNode
import kotlin.test.assertTrue

class TreeTerminalNegatedToken(psiElement: XtextNegatedToken, parent: TreeNode, cardinality: Cardinality) :
    TreeNodeImpl(parent, cardinality) {
    init {
        psiElement.terminalTokenElement.characterRange?.let {
            val terminalRange = TreeTerminalRange(it, this, Cardinality.NONE)
            _children.add(terminalRange)
        }
        psiElement.terminalTokenElement.parenthesizedTerminalElement?.let {
            if (isValidParenthesizedElement(it)) {
                val allRanges = PsiTreeUtil.findChildrenOfType(it, XtextCharacterRange::class.java)
                allRanges.forEach {
                    val terminalRange = TreeTerminalRange(it, this, Cardinality.NONE)
                    _children.add(terminalRange)
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
        val terminalGroupList = mutableListOf(element.terminalAlternatives.terminalGroup)
        element.terminalAlternatives.terminalAlternativesSuffix1?.let { terminalGroupList.addAll(it.terminalGroupList) }
        terminalGroupList
            .flatMap {
                val terminalTokenList = mutableListOf(it.terminalToken)
                it.terminalGroupSuffix1?.let { terminalTokenList.addAll(it.terminalTokenList) }
                terminalTokenList
            }
            .forEach {
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