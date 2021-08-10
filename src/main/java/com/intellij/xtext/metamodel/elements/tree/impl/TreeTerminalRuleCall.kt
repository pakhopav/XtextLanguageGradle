package com.intellij.xtext.metamodel.elements.tree.impl

import com.intellij.xtext.language.psi.XtextTerminalRuleCall
import com.intellij.xtext.metamodel.model.elements.Cardinality
import com.intellij.xtext.metamodel.model.elements.tree.TreeNode
import com.intellij.xtext.metamodel.model.elements.tree.TreeTerminalRule
import com.intellij.xtext.metamodel.model.elements.tree.eliminateCaret

class TreeTerminalRuleCall(psiElement: XtextTerminalRuleCall, parent: TreeNode, cardinality: Cardinality) :
    TreeNodeImpl(parent, cardinality) {
    val calledRuleName = psiElement.referenceAbstractRuleRuleID.ruleID.text.eliminateCaret().capitalize()
    var called: TreeTerminalRule? = null
    override fun getString(): String {
        return called!!.getString()
    }

}