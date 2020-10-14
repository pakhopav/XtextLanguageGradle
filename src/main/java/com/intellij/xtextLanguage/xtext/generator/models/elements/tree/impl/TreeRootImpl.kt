package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRoot
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.eliminateCaret
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule


open class TreeRootImpl(psiRule: XtextParserRule) : TreeNodeImpl(psiRule, null), TreeRoot {
    // TreeNode fields
    override val cardinality: Cardinality
        get() = Cardinality.NONE

    // TreeRoot fields
    override val name = psiRule.ruleNameAndParams.validID.text.eliminateCaret().capitalize()

    protected var changedReturnTypeText: String? = null
    override val returnTypeText: String = psiRule.typeRef?.text ?: ""
        get() {
            changedReturnTypeText?.let { return it }
            if (field.isEmpty()) {
                return if (_isDatatypeRule) "String"
                else ""
            } else {
                return field
            }
        }
    protected var _isDatatypeRule = false
    override val isDatatypeRule: Boolean
        get() = _isDatatypeRule
    override val isFragment: Boolean = psiRule.fragmentKeyword != null

    protected var _superRuleName: String? = null
    override val superRuleName: String?
        get() = _superRuleName

    override fun getBnfString(): String {
        specificString?.let { return it }
        val stringBuffer = StringBuffer()
        _children.forEach {
            stringBuffer.append(it.getBnfString())
            stringBuffer.append(" ")
        }
        return stringBuffer.toString()
    }


    override fun hasName(): Boolean {
        val nodesWithAssignmentToNameFeature = this.filterNodesInSubtree { it is TreeLeaf && it.assignment?.featureName == "name" }
        return nodesWithAssignmentToNameFeature.isNotEmpty()
    }

    fun setIsDatatypeRule(b: Boolean) {
        _isDatatypeRule = b
    }

    fun setSuperRule(superRuleName: String) {
        _superRuleName = superRuleName
    }

    fun changeReturnType(newReturnTypeText: String) {
        changedReturnTypeText = newReturnTypeText
    }
}
