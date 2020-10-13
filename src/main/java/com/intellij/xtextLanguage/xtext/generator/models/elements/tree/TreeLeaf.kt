package com.intellij.xtextLanguage.xtext.generator.models.elements.tree

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.TreeRewrite

interface TreeLeaf : TreeNode {
    val assignment: Assignment?
    val rewrite: TreeRewrite?
    val simpleActionText: String?

    fun getPsiElementTypeName(): String
    fun getBnfName(): String
}