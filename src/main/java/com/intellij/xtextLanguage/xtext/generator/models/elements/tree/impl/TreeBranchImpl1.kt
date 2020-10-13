package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeBranch
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextAssignableAlternatives

class TreeBranchImpl1(psiAlternatives: XtextAssignableAlternatives, parent: TreeNode) : TreeNodeImpl(psiAlternatives, parent), TreeBranch {
    override val cardinality: Cardinality
        get() = Cardinality.NONE

    override fun getBnfString(): String {
        specificString?.let { return it }
        return _children.map { it.getBnfString() }.joinToString(separator = " | ")
    }
}
