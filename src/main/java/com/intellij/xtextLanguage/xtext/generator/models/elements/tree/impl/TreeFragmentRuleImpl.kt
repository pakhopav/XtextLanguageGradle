package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeFragmentRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.eliminateCaret
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class TreeFragmentRuleImpl : TreeNodeImpl, TreeFragmentRule {
    // TreeNode fields
    override val cardinality: Cardinality
        get() = Cardinality.NONE

    override val name: String


    protected var _isDatatypeRule = false
    override val isDatatypeRule: Boolean
        get() = _isDatatypeRule


    constructor(psiRule: XtextParserRule) : super(null) {
        name = psiRule.ruleNameAndParams.validID.text.eliminateCaret().capitalize()

    }

    constructor(name: String) : super(null) {
        this.name = name
    }


    override fun getBnfString(): String {
        val stringBuffer = StringBuffer()
        stringBuffer.append("private $name ::= ")
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
}