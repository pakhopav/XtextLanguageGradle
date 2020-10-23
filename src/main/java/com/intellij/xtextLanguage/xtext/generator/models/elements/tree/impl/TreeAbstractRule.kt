package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.eliminateCaret
import com.intellij.xtextLanguage.xtext.psi.XtextEnumRule
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule
import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRule

abstract class TreeAbstractRule : TreeNodeImpl, TreeRule {
    protected var _returnType: EmfClassDescriptor
    override val returnType: EmfClassDescriptor
        get() = _returnType

    override val name: String


    protected var _isDatatypeRule = false
    override val isDatatypeRule: Boolean
        get() = _isDatatypeRule


    constructor(psiRule: XtextParserRule, type: EmfClassDescriptor) : super(null, Cardinality.NONE) {
        name = psiRule.ruleNameAndParams.validID.text.eliminateCaret().capitalize()
        _returnType = type
    }

    constructor(psiRule: XtextTerminalRule, type: EmfClassDescriptor) : super(null, Cardinality.NONE) {
        name = psiRule.validID.text.eliminateCaret().capitalize()
        _returnType = type
    }

    constructor(psiRule: XtextEnumRule, type: EmfClassDescriptor) : super(null, Cardinality.NONE) {
        name = psiRule.validID.text.eliminateCaret().capitalize()
        _returnType = type
    }

    constructor(name: String, type: EmfClassDescriptor) : super(null, Cardinality.NONE) {
        this.name = name
        _returnType = type
    }


    override fun getString(): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append("$name ::= ")
        _children.forEach {
            stringBuffer.append(it.getString())
            stringBuffer.append(" ")
        }
        return stringBuffer.toString()
    }

    fun setIsDatatypeRule(b: Boolean) {
        _isDatatypeRule = b
    }
}