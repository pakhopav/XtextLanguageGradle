package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.xtext.metamodel.elements.Cardinality
import com.intellij.xtext.metamodel.elements.emf.Assignment
import com.intellij.xtext.metamodel.elements.tree.TreeKeyword
import com.intellij.xtext.metamodel.elements.tree.TreeNode

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