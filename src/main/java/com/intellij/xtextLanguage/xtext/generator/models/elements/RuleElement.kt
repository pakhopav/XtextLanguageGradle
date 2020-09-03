package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement

abstract class RuleElement(open val psiElement: PsiElement) {
    var refactoredName: String? = null
    var action = ""
    abstract var assignment: String
    abstract fun getBnfName(): String


}