package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.psi.XtextEnumLiteralDeclaration
import com.intellij.xtextLanguage.xtext.psi.XtextEnumRule

class EnumRule(val myRule: XtextEnumRule) : ModelRule() {
    override val name = myRule.validID.text.replace("^", "Caret").capitalize()
    override var returnTypeText: String = myRule.typeRef?.text ?: name
    override val alternativeElements: MutableList<RuleElement> = PsiTreeUtil.findChildrenOfType(myRule, XtextEnumLiteralDeclaration::class.java).map { EnumRuleElement(it) }.toMutableList()
    override var isDataTypeRule = false

}