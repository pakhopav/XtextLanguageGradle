package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextKeyword

class TerminalKeywordElement(psiElement: XtextKeyword) : TerminalRuleElement(psiElement) {
    override var assignment = ""
    override fun getFlexName(): String {
        val sb = StringBuilder()
        getBnfName().toCharArray().forEach {
            if (it == '/') sb.append("\\$it")
            else if (it == ' ') sb.append("\"$it\"")
            else sb.append(it)
        }
        return sb.toString()
    }

    override fun getBnfName(): String {
        refactoredName?.let { return it }
        var psiElementText = psiElement.text
        psiElementText = psiElementText.substring(1, psiElementText.length - 1)
        val sb = StringBuilder()
        psiElementText.toCharArray().forEach {
            if (it in arrayOf('*', '.', '"', '+', '?', '^')) sb.append("\\$it")
            else sb.append(it)
        }
        return sb.toString()
    }
}