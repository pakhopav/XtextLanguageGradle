package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.psi.XtextEnumLiteralDeclaration
import com.intellij.xtextLanguage.xtext.psi.XtextEnumRule

class EnumRule(val myRule: XtextEnumRule) {
    val name = myRule.validID.text.replace("^", "Caret").capitalize()
    val returnType: String = myRule.typeRef?.text ?: name
    val alternativeElements = PsiTreeUtil.findChildrenOfType(myRule, XtextEnumLiteralDeclaration::class.java).map { EnumRuleElement(it) }


}