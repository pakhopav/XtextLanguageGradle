package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextCrossReference
import com.intellij.xtext.metamodel.model.elements.Cardinality
import com.intellij.xtext.metamodel.model.elements.emf.Assignment
import com.intellij.xtext.metamodel.model.elements.emf.EmfClassDescriptor
import com.intellij.xtext.metamodel.model.elements.names.NameGenerator
import com.intellij.xtext.metamodel.model.elements.tree.TreeCrossReference
import com.intellij.xtext.metamodel.model.elements.tree.TreeNode


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