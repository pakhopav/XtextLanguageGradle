package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextCrossReference

class ParserCrossReferenceElement(override val psiElement: XtextCrossReference, val containerName: String) : RuleElement(psiElement) {

    val name: String
    val referenceType = psiElement.crossReferenceableTerminal?.text ?: "ID"
    val referenceTargetText = psiElement.typeRef.text
    val targets = mutableListOf<CrossReferenceTarget>()

    init {
        name = createReferenceName()
    }

//    override fun equals(other: Any?): Boolean {
//        if ((other as? ParserCrossReferenceElement)?.name == name) return true
//        return false
//    }
//
//    override fun hashCode(): Int {
//        return name.hashCode()
//    }


    override var assignment = ""

    override fun getBnfName(): String {
        return refactoredName ?: name
    }

    private fun createReferenceName(): String {
        refactoredName?.let { return it }
        var targetText = if (referenceTargetText.contains("::")) referenceTargetText.split("::")[1] else referenceTargetText
//        if (targetText.contains("::")) targetText = targetText.slice(targetText.indexOf(":") + 2..targetText.length - 1)
        var name = "REFERENCE_${targetText}"
        referenceType?.let { name = name + "_$it" }
        return name
    }

}