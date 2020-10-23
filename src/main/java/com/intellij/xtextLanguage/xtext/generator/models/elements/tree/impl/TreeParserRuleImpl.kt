package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule


open class TreeParserRuleImpl : TreeAbstractRule, TreeParserRule {

    protected var _superRuleName: String? = null
    override val superRuleName: String?
        get() = _superRuleName

    protected var _isReferenced = false
    override val isReferenced: Boolean
        get() = _isReferenced

    override val isSuffix: Boolean

    constructor(psiRule: XtextParserRule, returnType: EmfClassDescriptor, isSuffix: Boolean = false) : super(psiRule, returnType) {
        this.isSuffix = isSuffix
    }

    constructor(name: String, returnType: EmfClassDescriptor, isSuffix: Boolean = false) : super(name, returnType) {
        this.isSuffix = isSuffix
    }

    override fun hasNameFeature(): Boolean {
        val nodesWithAssignmentToNameFeature = this.filterNodesInSubtree { it is TreeLeaf && it.assignment?.featureName == "name" }
        return nodesWithAssignmentToNameFeature.isNotEmpty()
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
