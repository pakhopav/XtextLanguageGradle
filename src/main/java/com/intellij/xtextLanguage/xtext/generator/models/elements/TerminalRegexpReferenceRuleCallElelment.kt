package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRuleCall

class TerminalRegexpReferenceRuleCallElelment(psiElement: XtextTerminalRuleCall) : RuleElement(psiElement) {
    override fun getBnfName(): String {
        return psiElement.text
    }
}