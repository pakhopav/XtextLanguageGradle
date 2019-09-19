package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRule

class TerminalRule(val myRule: XtextTerminalRule) {
    val name = myRule.getName()
    var isFragment: Boolean = myRule.fragment != null
    val returnType: String? = myRule.typeRef?.text
}