package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement

class TerminalSimpleElement(psiElement: PsiElement) : TerminalRuleElement(psiElement) {
    override var assignment = ""
    override fun getFlexName(): String {
        return getBnfName()
    }

    override fun getBnfName(): String {
        refactoredName?.let { return it }
        return psiElement.text
    }
}