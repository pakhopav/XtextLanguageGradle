package com.intellij.xtext.metamodel

import com.intellij.xtext.metamodel.model.elements.Keyword
import com.intellij.xtext.metamodel.model.elements.tree.TreeLeaf
import com.intellij.xtext.metamodel.model.elements.tree.TreeRule
import com.intellij.xtext.metamodel.model.elements.tree.TreeRuleCall
import com.intellij.xtext.metamodel.model.elements.tree.TreeTerminalRule

interface MetaContext {
    val rules: List<TreeRule>
    val terminalRules: List<TreeTerminalRule>
    val keywords: List<Keyword>

    fun getRuleByName(name: String): TreeRule

    fun findLiteralAssignmentsInRule(rule: TreeRule): List<TreeLeaf>

    fun findObjectAssignmentsInRule(rule: TreeRule): List<TreeRuleCall>

}