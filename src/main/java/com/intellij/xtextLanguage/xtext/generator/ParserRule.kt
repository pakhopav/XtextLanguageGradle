package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class ParserRule(val myRule: XtextParserRule) {
    val name = myRule.ruleNameAndParams.text
    var isFragment: Boolean = myRule.fragment != null
    val returnType: String? = myRule.typeRef?.text

}