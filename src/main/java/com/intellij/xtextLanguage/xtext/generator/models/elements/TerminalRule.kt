package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitorTerminalRules
import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRule

class TerminalRule(val myRule: XtextTerminalRule) {
    val name = myRule.validID.text
    var isFragment: Boolean = myRule.fragmentKeyword != null
    val returnType: String = myRule.typeRef?.text ?: name
    val alterntivesElements = XtextVisitorTerminalRules.getRuleElementListOfTerminalRule(myRule)


}