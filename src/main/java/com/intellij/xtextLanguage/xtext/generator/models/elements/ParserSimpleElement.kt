package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement

class ParserSimpleElement(psiElement: PsiElement) : RuleElement(psiElement) {
    override fun getBnfName(): String {
        return psiElement.text
    }
}