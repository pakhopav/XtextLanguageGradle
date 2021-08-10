package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextPredicatedRuleCall
import com.intellij.xtext.language.psi.XtextRuleCall
import com.intellij.xtext.metamodel.model.elements.Cardinality
import com.intellij.xtext.metamodel.model.elements.emf.Assignment
import com.intellij.xtext.metamodel.model.elements.names.NameGenerator
import com.intellij.xtext.metamodel.model.elements.tree.TreeNode
import com.intellij.xtext.metamodel.model.elements.tree.TreeRule
import com.intellij.xtext.metamodel.model.elements.tree.TreeRuleCall
import com.intellij.xtext.metamodel.model.elements.tree.eliminateCaret


class TreeRuleCallImpl : TreeLeafImpl, TreeRuleCall {

    constructor(psiElement: XtextRuleCall,
                parent: TreeNode,
                cardinality: Cardinality,
                assignment: Assignment? = null) : super(psiElement, parent, cardinality, assignment)

    constructor(psiElement: XtextPredicatedRuleCall,
                parent: TreeNode,
                cardinality: Cardinality,
                assignment: Assignment? = null) : super(psiElement, parent, cardinality, assignment)


    private val bnfName = psiElement.text.eliminateCaret().capitalize()
    var called: TreeRule? = null


    override fun getString(): String {
        return bnfName + cardinality.toString()
    }

    override fun getCalledRule(): TreeRule? {
        return called
    }

    override fun getPsiElementTypeName(): String {
        return NameGenerator.toGKitTypesName(bnfName)
    }

    override fun getBnfName(): String {
        return bnfName
    }

}
