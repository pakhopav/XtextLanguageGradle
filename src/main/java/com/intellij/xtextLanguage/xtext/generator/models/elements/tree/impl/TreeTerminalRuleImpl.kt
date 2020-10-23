package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeTerminalRule
import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRule

class TreeTerminalRuleImpl : TreeAbstractRule, TreeTerminalRule {

    override fun getString(): String {
        return _children.map { it.getString() }.joinToString(separator = "")
    }


    override val isDatatypeRule = true

    constructor(psiRule: XtextTerminalRule, type: EmfClassDescriptor) : super(psiRule, type)

    constructor(name: String, type: EmfClassDescriptor) : super(name, type)

}