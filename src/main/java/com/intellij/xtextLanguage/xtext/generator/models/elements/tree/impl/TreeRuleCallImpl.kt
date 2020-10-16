package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRoot
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRuleCall
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.eliminateCaret


class TreeRuleCallImpl(psiElement: PsiElement,
                       parent: TreeNode,
                       assignment: Assignment? = null) : TreeLeafImpl(psiElement, parent, assignment), TreeRuleCall {

    override val cardinality: Cardinality
        get() = getCardinalityOfPsiElement()

    private val bnfName = psiElement.text.eliminateCaret().capitalize()
    var fragmentRule: TreeRoot? = null


    override fun getBnfString(): String {
        return bnfName + cardinality.toString()
    }

    override fun getCalledFragmentRule(): TreeRoot? {
        return fragmentRule
    }

    override fun getPsiElementTypeName(): String {
        return NameGenerator.toGKitTypesName(bnfName)
    }

    override fun getBnfName(): String {
        return bnfName
    }

}
