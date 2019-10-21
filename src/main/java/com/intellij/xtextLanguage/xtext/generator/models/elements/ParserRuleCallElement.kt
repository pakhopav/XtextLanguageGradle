package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEAbstractRuleRuleID

class ParserRuleCallElement(override val psiElement: XtextREFERENCEAbstractRuleRuleID) : RuleElement(psiElement) {
    override fun getBnfName(): String {
        return psiElement.text.replace("^", "Caret").capitalize()
    }
}