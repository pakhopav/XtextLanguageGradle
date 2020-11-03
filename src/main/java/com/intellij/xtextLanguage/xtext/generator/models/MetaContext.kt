package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.Keyword
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRuleCall
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeTerminalRule

interface MetaContext {
    val rules: List<TreeRule>
    val terminalRules: List<TreeTerminalRule>
    val keywords: List<Keyword>

    fun getRuleByName(name: String): TreeRule

    fun findLiteralAssignmentsInRule(rule: TreeRule): List<TreeLeaf>

    fun findObjectAssignmentsInRule(rule: TreeRule): List<TreeRuleCall>

}