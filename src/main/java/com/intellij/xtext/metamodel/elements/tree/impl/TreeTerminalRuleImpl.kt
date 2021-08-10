package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextTerminalRule
import com.intellij.xtext.metamodel.elements.emf.EmfClassDescriptor
import com.intellij.xtext.metamodel.elements.tree.TreeTerminalRule

class TreeTerminalRuleImpl : TreeAbstractRule, TreeTerminalRule {

    override fun getString(): String {
        return _children.map { it.getString() }.joinToString(separator = "")
    }


    override val isDatatypeRule = true

    constructor(psiRule: XtextTerminalRule, type: EmfClassDescriptor) : super(psiRule, type)

    constructor(name: String, type: EmfClassDescriptor) : super(name, type)

}