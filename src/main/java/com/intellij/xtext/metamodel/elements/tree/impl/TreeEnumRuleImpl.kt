package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextEnumRule
import com.intellij.xtext.metamodel.elements.emf.EmfClassDescriptor
import com.intellij.xtext.metamodel.elements.tree.TreeEnumRule

class TreeEnumRuleImpl : TreeAbstractRule, TreeEnumRule {
    override val isDatatypeRule = true

    constructor(psiEnumRule: XtextEnumRule, type: EmfClassDescriptor) : super(psiEnumRule, type)

    constructor(name: String, type: EmfClassDescriptor) : super(name, type)
}