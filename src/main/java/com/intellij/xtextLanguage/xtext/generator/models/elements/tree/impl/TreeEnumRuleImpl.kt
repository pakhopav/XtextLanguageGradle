package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeEnumRule
import com.intellij.xtextLanguage.xtext.psi.XtextEnumRule

class TreeEnumRuleImpl : TreeAbstractRule, TreeEnumRule {
    override val isDatatypeRule = true

    constructor(psiEnumRule: XtextEnumRule, type: EmfClassDescriptor) : super(psiEnumRule, type)

    constructor(name: String, type: EmfClassDescriptor) : super(name, type)
}