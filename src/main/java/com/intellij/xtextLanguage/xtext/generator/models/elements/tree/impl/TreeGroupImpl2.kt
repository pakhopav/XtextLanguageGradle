package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeGroup
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.psi.XtextUnorderedGroup

class TreeGroupImpl2(psiElement: XtextUnorderedGroup, parent: TreeNode) : TreeNodeImpl(psiElement, parent), TreeGroup {
    override val cardinality: Cardinality
        get() = Cardinality.NONE

    override fun getBnfString(): String {
        specificString?.let { return it }
        return _children.map { it.getBnfString() }.joinToString(separator = " ")
    }

}