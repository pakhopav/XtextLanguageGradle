package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement

class TerminalSimpleElement(psiElement: PsiElement) : TerminalRuleElement(psiElement) {
    override fun getFlexName(): String {
        return getBnfName()
    }

    override fun getBnfName(): String {
        return psiElement.text
    }
}