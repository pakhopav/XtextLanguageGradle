package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeCrossReference
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextCrossReference


class TreeCrossReferenceImpl(psiCrossReference: XtextCrossReference,
                             override val containerRuleName: String,
                             parent: TreeNode,
                             cardinality: Cardinality,
                             override val targetType: EmfClassDescriptor,
                             assignment: Assignment) : TreeLeafImpl(psiCrossReference, parent, cardinality, assignment), TreeCrossReference {

    override val assignment = assignment


    override fun getString(): String {
        return bnfName + cardinality.toString()
    }

    val referenceType = psiCrossReference.crossReferenceableTerminal?.text ?: "ID"

    private val bnfName = createReferenceName()

    private fun createReferenceName(): String {
        return "REFERENCE_${targetType.className}_$referenceType"
    }

    override fun getPsiElementTypeName(): String {
        return NameGenerator.toGKitTypesName(getBnfName())
    }

    override fun getBnfName(): String {
        return bnfName
    }

}