package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextParserRule
import com.intellij.xtext.metamodel.model.elements.emf.EmfClassDescriptor
import com.intellij.xtext.metamodel.model.elements.tree.TreeFragmentRule

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