package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEAbstractRuleRuleID

class ParserRuleCallElement(override val psiElement: XtextREFERENCEAbstractRuleRuleID) : RuleElement(psiElement) {
    override var assignment = ""
    override fun getBnfName(): String {
        refactoredName?.let { return it }
        return psiElement.text.replace("^", "Caret").capitalize()
    }
}