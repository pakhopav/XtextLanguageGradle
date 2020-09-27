package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement

interface ModelRuleCreator {

    fun createRule(psiRule: PsiElement): CreationOutput?

    interface CreationOutput {
        fun getRule(): ModelRule?
        fun getSuffixList(): List<ModelRule>
    }
}