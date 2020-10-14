package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeGroup
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractTokenWithCardinality
import com.intellij.xtextLanguage.xtext.psi.XtextParenthesizedElement


class TreeGroupImpl(psiElement: XtextParenthesizedElement, parent: TreeNode, assignment: Assignment? = null) : TreeNodeImpl(parent), TreeGroup {
    override val cardinality = getCardinalityOfPsiElement(psiElement)
    override fun getBnfString(): String {
        specificString?.let { return it }
        return _children.map { it.getBnfString() }.joinToString(separator = " ", prefix = "(", postfix = ")") + cardinality.toString()
    }

    private fun getCardinalityOfPsiElement(psiElement: XtextParenthesizedElement): Cardinality {
        val psiParentWithCardinality = PsiTreeUtil.getParentOfType(psiElement, XtextAbstractTokenWithCardinality::class.java)
        psiParentWithCardinality?.let {
            psiParentWithCardinality.asteriskKeyword?.let { return Cardinality.ASTERISK }
            psiParentWithCardinality.quesMarkKeyword?.let { return Cardinality.QUES }
            psiParentWithCardinality.plusKeyword?.let { return Cardinality.PLUS }
        }
        return Cardinality.NONE
    }
}
