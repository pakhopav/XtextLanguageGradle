package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeFragmentRule
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class TreeFragmentRuleImpl : TreeAbstractRule, TreeFragmentRule {

    constructor(psiRule: XtextParserRule) : super(psiRule, EmfClassDescriptor.STRING)

    constructor(name: String) : super(name, EmfClassDescriptor.STRING)

    override fun getString(): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append("private $name ::= ")
        _children.forEach {
            stringBuffer.append(it.getString())
            stringBuffer.append(" ")
        }
        return stringBuffer.toString()
    }

}