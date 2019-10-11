package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.XtextAssignableTerminal
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class ParserRule(val myRule: XtextParserRule) {
    var name = myRule.ruleNameAndParams.validID.text
    var isFragment: Boolean = myRule.fragment != null
    var returnType: String = myRule.typeRef?.text ?: name
    val attributes: MutableMap<String, XtextAssignableTerminal> = HashMap()
    var isNamed = false

    fun setNamed() {
        isNamed = true
    }

}