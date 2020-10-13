package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRuleCall

class TerminalRuleCallElelment(psiElement: XtextTerminalRuleCall) : TerminalRuleElement(psiElement) {
    override var assignment = ""
    private var _flexName = ""
    private var _bnfName = ""
    override fun getFlexName(): String {
        return _flexName
//        val sb = StringBuilder()
//        resolver.getTerminalRuleByName(psiElement.text)?.let {
//            it.alternativeElements.map { it as TerminalRuleElement }.forEach {
//                sb.append(it.getFlexName())
//            }
//        }
//        return sb.toString()
    }

    fun setFlexName(string: String) {
        _flexName = string
    }

    override fun getBnfName(): String {
        refactoredName?.let { return it }
        return _bnfName
//        val sb = StringBuilder()
//        resolver.getTerminalRuleByName(psiElement.text)?.let {
//            it.alternativeElements.forEach {
//                sb.append(it.getBnfName())
//            }
//        }
//        return sb.toString()
    }

    fun setBnfName(string: String) {
        _bnfName = string
    }
}