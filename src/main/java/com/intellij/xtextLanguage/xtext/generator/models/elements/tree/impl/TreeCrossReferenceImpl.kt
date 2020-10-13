package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeCrossReference
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextCrossReference


class TreeCrossReferenceImpl(psiCrossReference: XtextCrossReference,
                             override val containerRuleName: String,
                             parent: TreeNode,
                             assignment: Assignment) : TreeLeafImpl(psiCrossReference, parent, assignment), TreeCrossReference {
    override val targetTypeText = psiCrossReference.typeRef.text
    override val cardinality: Cardinality
        get() = getCardinalityOfPsiElement()
    override val assignment = assignment


    override fun getBnfString(): String {
        specificString?.let { return it }
        return bnfName + cardinality.toString()
    }

    val referenceType = psiCrossReference.crossReferenceableTerminal?.text ?: "ID"

    private val bnfName = createReferenceName()
    private fun createReferenceName(): String {
        var targetText = if (targetTypeText.contains("::")) targetTypeText.split("::")[1] else targetTypeText
        var name = "REFERENCE_${targetText}_$referenceType"
        return name
    }

    override fun getPsiElementTypeName(): String {
        return NameGenerator.toGKitTypesName(getBnfString())
    }

    override fun getBnfName(): String {
        return bnfName
    }

}