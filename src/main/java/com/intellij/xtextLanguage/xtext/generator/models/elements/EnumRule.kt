package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.psi.XtextEnumLiteralDeclaration
import com.intellij.xtextLanguage.xtext.psi.XtextEnumRule

class EnumRule(val myRule: XtextEnumRule) : ModelRule() {
    override val name = myRule.validID.text.replace("^", "Caret").capitalize()
    override val returnType: String = myRule.typeRef?.text ?: name
    override var alternativeElements: MutableList<out RuleElement> = PsiTreeUtil.findChildrenOfType(myRule, XtextEnumLiteralDeclaration::class.java).map { EnumRuleElement(it) }.toMutableList()
    override var isDataTypeRule = false

}