package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.XtextEnumRule

class EnumRule(val myRule: XtextEnumRule) {
    val name = myRule.validID?.text
    val returnType: String? = myRule.typeRef?.text
}