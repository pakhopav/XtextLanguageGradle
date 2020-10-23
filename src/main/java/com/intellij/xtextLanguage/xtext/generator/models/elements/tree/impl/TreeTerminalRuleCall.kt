package com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeTerminalRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.eliminateCaret
import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRuleCall

class TreeTerminalRuleCall(psiElement: XtextTerminalRuleCall, parent: TreeNode, cardinality: Cardinality) : TreeNodeImpl(parent, cardinality) {
    val calledRuleName = psiElement.referenceAbstractRuleRuleID.ruleID.text.eliminateCaret().capitalize()
    var called: TreeTerminalRule? = null
    override fun getString(): String {
        return called!!.getString()
    }

}