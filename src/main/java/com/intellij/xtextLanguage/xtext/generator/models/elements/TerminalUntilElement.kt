package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextUntilToken

class TerminalUntilElement(override val psiElement: XtextUntilToken) : TerminalRuleElement(psiElement) {
    override fun getFlexName(): String {
        psiElement.terminalTokenElement.characterRange?.let {
            if (it.keywordList.size == 1) {
                return "([^\"*\"]*(\"*\"+[^\"*\"\"/\"])?)*(\"*\"+\"/\")?${TerminalKeywordElement(it.keywordList.first()).getFlexName()}"
            }
        }
        return ""
    }

    override fun getBnfName(): String {
        psiElement.terminalTokenElement.characterRange?.let {
            if (it.keywordList.size == 1) {
                return "(?s).*${TerminalKeywordElement(it.keywordList.first()).getBnfName()}"
            }
        }
        return ""
    }
}