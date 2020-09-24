package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement

class ParserRuleCallElement(override val psiElement: PsiElement) : RuleElement(psiElement) {


    override var assignment = ""
    override fun getBnfName(): String {
        refactoredName?.let { return it }
        return psiElement.text.replace("^", "Caret").capitalize()
    }
}