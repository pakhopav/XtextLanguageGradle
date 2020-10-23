package com.intellij.xtextLanguage.xtext.generator.models.elements.tree

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor

interface TreeRule : TreeNode {
    val name: String
    val isDatatypeRule: Boolean
    val returnType: EmfClassDescriptor
}