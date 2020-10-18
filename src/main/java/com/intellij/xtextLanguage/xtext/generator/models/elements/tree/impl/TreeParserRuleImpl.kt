package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule


open class TreeParserRuleImpl : TreeRootImpl, TreeParserRule {

    protected var _returnType: EmfClassDescriptor
    override val returnType: EmfClassDescriptor
        get() = _returnType

    protected var _superRuleName: String? = null
    override val superRuleName: String?
        get() = _superRuleName

    protected var _isReferenced = false
    override val isReferenced: Boolean
        get() = _isReferenced

    override val isSuffix: Boolean


    constructor(psiRule: XtextParserRule, returnType: EmfClassDescriptor, isSuffix: Boolean = false) : super(psiRule) {
        _returnType = returnType
        this.isSuffix = isSuffix
    }

    constructor(name: String, returnType: EmfClassDescriptor, isSuffix: Boolean = false) : super(name) {
        _returnType = returnType
        this.isSuffix = isSuffix
    }



    fun setSuperRule(superRuleName: String) {
        _superRuleName = superRuleName
    }

    fun setReturnType(typeDescriptor: EmfClassDescriptor) {
        _returnType = typeDescriptor
    }

    fun setIsReferenced(referenced: Boolean) {
        _isReferenced = referenced
    }

}
