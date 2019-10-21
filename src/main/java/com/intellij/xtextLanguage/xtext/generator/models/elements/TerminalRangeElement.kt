package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextCharacterRange

class TerminalRangeElement(override val psiElement: XtextCharacterRange) : TerminalRuleElement(psiElement) {
    override fun getFlexName(): String {
        if (psiElement.keywordList.size == 1) {
            return TerminalKeywordElement(psiElement.keywordList.first()).getFlexName()
        }
        if (psiElement.keywordList.size == 2) {
            return "[${TerminalKeywordElement(psiElement.keywordList.get(0)).getFlexName()}-${TerminalKeywordElement(psiElement.keywordList.get(1)).getFlexName()}]"
        }
        return ""
    }

    override fun getBnfName(): String {
        if (psiElement.keywordList.size == 1) {
            return TerminalKeywordElement(psiElement.keywordList.first()).getBnfName()
        }
        if (psiElement.keywordList.size == 2) {
            return "[${TerminalKeywordElement(psiElement.keywordList.get(0)).getBnfName()}-${TerminalKeywordElement(psiElement.keywordList.get(1)).getBnfName()}]"
        }
        return ""
    }
}