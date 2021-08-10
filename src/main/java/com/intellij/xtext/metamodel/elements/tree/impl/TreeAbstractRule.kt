package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextEnumRule
import com.intellij.xtext.language.psi.XtextParserRule
import com.intellij.xtext.language.psi.XtextTerminalRule
import com.intellij.xtext.metamodel.model.elements.Cardinality
import com.intellij.xtext.metamodel.model.elements.emf.EmfClassDescriptor
import com.intellij.xtext.metamodel.model.elements.tree.TreeRule
import com.intellij.xtext.metamodel.model.elements.tree.eliminateCaret

abstract class TreeAbstractRule : TreeNodeImpl, TreeRule {
    protected var _returnType: EmfClassDescriptor
    override val returnType: EmfClassDescriptor
        get() = _returnType

    override val name: String


    protected var _isDatatypeRule = false
    override val isDatatypeRule: Boolean
        get() = _isDatatypeRule


    constructor(psiRule: XtextParserRule, type: EmfClassDescriptor) : super(null, Cardinality.NONE) {
        name = psiRule.validID.text.eliminateCaret().capitalize()
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