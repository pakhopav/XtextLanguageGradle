package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeGroup
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextParenthesizedElement


class TreeGroupImpl(psiElement: XtextParenthesizedElement, parent: TreeNode, assignment: Assignment? = null) : TreeNodeImpl(psiElement, parent), TreeGroup {
    override val cardinality = getCardinalityOfPsiElement()
    override fun getBnfString(): String {
        specificString?.let { return it }
        return _children.map { it.getBnfString() }.joinToString(separator = " ", prefix = "(", postfix = ")") + cardinality.toString()
    }

}
