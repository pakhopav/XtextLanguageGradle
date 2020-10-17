package com.intellij.xtextLanguage.xtext.generator.models.elements.tree

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor

interface TreeParserRule : TreeRoot {
    val returnType: EmfClassDescriptor
    val superRuleName: String?
    val isReferenced: Boolean
}