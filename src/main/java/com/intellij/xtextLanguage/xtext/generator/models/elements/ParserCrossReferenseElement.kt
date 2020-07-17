package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextCrossReference

class ParserCrossReferenseElement(override val psiElement: XtextCrossReference) : RuleElement(psiElement) {
    val name: String
    val referenceType = psiElement.crossReferenceableTerminal?.text ?: "ID"
    val refetenceTarget = psiElement.typeRef
    var targets = listOf<ParserRule>()

    init {
        name = createReferenceName()
    }

    override fun equals(other: Any?): Boolean {
        if ((other as? ParserCrossReferenseElement)?.name == name) return true
        return false
    }

    override fun getBnfName(): String {
        return name
    }

    fun createReferenceName(): String {
        var targetText = refetenceTarget.text
        if (targetText.contains("::")) targetText = targetText.slice(targetText.indexOf(":") + 2..targetText.length - 1)
        var name = "REFERENCE_${targetText}"
        referenceType?.let { name = name + "_$it" }
        return name
    }

}