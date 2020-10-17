package com.intellij.xtextLanguage.xtext.generator.models.elements.tree

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.Assignment
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor

interface TreeCrossReference : TreeLeaf {
    override val assignment: Assignment
    val containerRuleName: String
    val targetType: EmfClassDescriptor
}