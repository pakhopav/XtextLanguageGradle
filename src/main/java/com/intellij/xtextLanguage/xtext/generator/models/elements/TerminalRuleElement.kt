package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement

abstract class TerminalRuleElement(psiElement: PsiElement) : RuleElement(psiElement) {
    abstract fun getFlexName(): String
}