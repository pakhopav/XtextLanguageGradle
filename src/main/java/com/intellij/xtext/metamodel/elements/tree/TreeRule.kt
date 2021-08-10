package com.intellij.xtext.metamodel.elements.tree

import com.intellij.xtext.metamodel.elements.emf.EmfClassDescriptor


interface TreeRule : TreeNode {
    val name: String
    val isDatatypeRule: Boolean
    val returnType: EmfClassDescriptor
}