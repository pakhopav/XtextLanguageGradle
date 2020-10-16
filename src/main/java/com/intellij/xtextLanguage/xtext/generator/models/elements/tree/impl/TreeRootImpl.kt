package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRoot
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.eliminateCaret
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule


open class TreeRootImpl : TreeNodeImpl, TreeRoot {

    // TreeNode fields
    override val cardinality: Cardinality
        get() = Cardinality.NONE

    override val name: String

    var returnTypeText: String
        get() {
            if (field.isEmpty()) {
                if (_isDatatypeRule) return "String"
                else if (!isFragment) return name
                else return ""
            } else {
                return field
            }
        }

    protected var _isDatatypeRule = false
    override val isDatatypeRule: Boolean
        get() = _isDatatypeRule
    override val isFragment: Boolean

    protected var _superRuleName: String? = null
    override val superRuleName: String?
        get() = _superRuleName


    constructor(psiRule: XtextParserRule) : super(null) {
        name = psiRule.ruleNameAndParams.validID.text.eliminateCaret().capitalize()
        returnTypeText = psiRule.typeRef?.text ?: ""
        isFragment = psiRule.fragmentKeyword != null
    }

    constructor(name: String, returnTypeText: String, isFragment: Boolean) : super(null) {
        this.name = name
        this.returnTypeText = returnTypeText
        this.isFragment = isFragment
    }


    override fun getBnfString(): String {
        val stringBuffer = StringBuffer()
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


}
