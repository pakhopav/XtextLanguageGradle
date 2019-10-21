package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement

abstract class RuleElement(open val psiElement: PsiElement) {
    abstract fun getBnfName(): String
}