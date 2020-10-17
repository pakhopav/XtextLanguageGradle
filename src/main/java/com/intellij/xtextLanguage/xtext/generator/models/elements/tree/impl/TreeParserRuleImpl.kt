package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.eliminateCaret
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule


open class TreeParserRuleImpl : TreeNodeImpl, TreeParserRule {

    // TreeNode fields
    override val cardinality: Cardinality
        get() = Cardinality.NONE

    override val name: String

    protected var _returnType: EmfClassDescriptor
    override val returnType: EmfClassDescriptor
        get() = _returnType


    protected var _isDatatypeRule = false
    override val isDatatypeRule: Boolean
        get() = _isDatatypeRule

    protected var _superRuleName: String? = null
    override val superRuleName: String?
        get() = _superRuleName

    protected var _isReferenced = false
    override val isReferenced: Boolean
        get() = _isReferenced


    constructor(psiRule: XtextParserRule, returnType: EmfClassDescriptor) : super(null) {
        name = psiRule.ruleNameAndParams.validID.text.eliminateCaret().capitalize()
        _returnType = returnType
    }

    constructor(name: String, returnType: EmfClassDescriptor) : super(null) {
        this.name = name
        _returnType = returnType

    }


    override fun getBnfString(): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append("$name ::= ")
        _children.forEach {
            stringBuffer.append(it.getBnfString())
            stringBuffer.append(" ")
        }
        return stringBuffer.toString()
    }

    override fun hasNameFeature(): Boolean {
        val nodesWithAssignmentToNameFeature = this.filterNodesInSubtree { it is TreeLeaf && it.assignment?.featureName == "name" }
        return nodesWithAssignmentToNameFeature.isNotEmpty()
    }

    fun setIsDatatypeRule(b: Boolean) {
        _isDatatypeRule = b
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
