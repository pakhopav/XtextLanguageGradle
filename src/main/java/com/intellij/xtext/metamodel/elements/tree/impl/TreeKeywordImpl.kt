package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.xtext.metamodel.model.elements.Cardinality
import com.intellij.xtext.metamodel.model.elements.emf.Assignment
import com.intellij.xtext.metamodel.model.elements.tree.TreeKeyword
import com.intellij.xtext.metamodel.model.elements.tree.TreeNode

class TreeKeywordImpl(psiElement: PsiElement,
                      parent: TreeNode,
                      cardinality: Cardinality,
                      private val keywordName: String,
                      assignment: Assignment? = null) : TreeLeafImpl(psiElement, parent, cardinality, assignment), TreeKeyword {

    override fun getString(): String {
        return psiElement.text + cardinality.toString()
    }

    override fun getPsiElementTypeName(): String {
        return keywordName
    }

    override fun getBnfName(): String {
        return psiElement.text
    }

}