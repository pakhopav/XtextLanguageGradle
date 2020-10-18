package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRoot
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRuleCall
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.eliminateCaret
import com.intellij.xtextLanguage.xtext.psi.XtextPredicatedRuleCall
import com.intellij.xtextLanguage.xtext.psi.XtextRuleCall


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
    var called: TreeRoot? = null


    override fun getBnfString(): String {
        return bnfName + cardinality.toString()
    }

    override fun getCalledRule(): TreeRoot? {
        return called
    }

    override fun getPsiElementTypeName(): String {
        return NameGenerator.toGKitTypesName(bnfName)
    }

    override fun getBnfName(): String {
        return bnfName
    }

}
