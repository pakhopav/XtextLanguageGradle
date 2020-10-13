package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeSuffix

class TreeSuffixImpl(psiElement: PsiElement, val suffixName: String, parent: TreeNode, assignment: Assignment? = null) : TreeLeafImpl(psiElement, parent, assignment), TreeSuffix {
    override val cardinality: Cardinality
        get() = Cardinality.NONE
    override val assignment = null
    override fun getBnfString(): String {
        specificString?.let { return it }
        return suffixName
    }

    override fun getPsiElementTypeName(): String {
        return NameGenerator.toGKitTypesName(suffixName)
    }

    override fun getBnfName(): String {
        return suffixName
    }
}