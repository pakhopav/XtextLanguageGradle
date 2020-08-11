package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.generator.RuleResolver
import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRuleCall

class TerminalRuleCallElelment(psiElement: XtextTerminalRuleCall, val resolver: RuleResolver) : TerminalRuleElement(psiElement) {
    override var assignment = ""
    override fun getFlexName(): String {
        val sb = StringBuilder()
        resolver.getTerminalRuleByName(psiElement.text)?.let {
            it.alterntiveElements.forEach {
                sb.append(it.getFlexName())
            }
        }
        return sb.toString()
    }

    override fun getBnfName(): String {
        refactoredName?.let { return it }
        val sb = StringBuilder()
        resolver.getTerminalRuleByName(psiElement.text)?.let {
            it.alterntiveElements.forEach {
                sb.append(it.getBnfName())
            }
        }
        return sb.toString()
    }
}