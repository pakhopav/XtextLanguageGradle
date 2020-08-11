package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextEnumLiteralDeclaration

class EnumRuleElement(override val psiElement: XtextEnumLiteralDeclaration) : RuleElement(psiElement) {
    override var assignment = ""

    override fun getBnfName(): String {
        refactoredName?.let { return it }
        val sb = StringBuilder()
        sb.append(psiElement.referenceEcoreEEnumLiteral.text)
        psiElement.equalsKeyword?.let {
            sb.append(" | ${psiElement.keyword?.text}")

        }
        return sb.toString()
    }
}