package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextKeyword
import com.intellij.xtext.metamodel.model.elements.Cardinality
import com.intellij.xtext.metamodel.model.elements.tree.TreeNode

class TreeTerminalKeyword(private val psiElement: XtextKeyword, parent: TreeNode, cardinality: Cardinality) : TreeNodeImpl(parent, cardinality) {


    override fun getString(): String {
        val psiElementText = psiElement.text.substring(1, psiElement.text.length - 1)
        val sb = StringBuffer()
        psiElementText.toCharArray().forEach {
            if (it in arrayOf('*', '.', '"', '+', '?', '^')) sb.append("\\$it")
            else sb.append(it)
        }
        var bufferString = sb.toString()
        bufferString = bufferString.replace("/", "\\/").replace(" ", "\" \"")
        bufferString += cardinality
        return bufferString
    }

}