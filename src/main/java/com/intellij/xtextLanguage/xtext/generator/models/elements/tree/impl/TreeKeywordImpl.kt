package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeKeyword
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode

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