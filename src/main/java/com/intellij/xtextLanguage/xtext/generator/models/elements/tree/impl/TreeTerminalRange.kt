package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextCharacterRange

class TreeTerminalRange(psiElement: XtextCharacterRange, parent: TreeNode, cardinality: Cardinality) : TreeNodeImpl(parent, cardinality) {

    init {
        //Check if it is legal expression
        if (psiElement.keywordList.size == 2) {
            if (psiElement.keywordList.get(0).text.length != 3
                    || psiElement.keywordList.get(1).text.length != 3
                    || psiElement.keywordList.get(0).text.get(1).toInt() > psiElement.keywordList.get(1).text.get(1).toInt()) {
                throw Exception("Wrong range terminal")
            }
        }

        val firstKeyword = TreeTerminalKeyword(psiElement.keywordList[0], this, Cardinality.NONE)
        this.addChild(firstKeyword)
        if (psiElement.keywordList.size > 1) {
            val secondKeyword = TreeTerminalKeyword(psiElement.keywordList[1], this, Cardinality.NONE)
            this.addChild(secondKeyword)
        }

    }


    override fun getString(): String {
        if (_children.size == 1) return _children[0].getString() + cardinality
        else {
            return "[${_children[0].getString()}-${_children[1].getString()}]" + cardinality
        }
    }
}