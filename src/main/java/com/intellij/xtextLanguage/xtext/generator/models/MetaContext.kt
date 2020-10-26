package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.*

interface MetaContext {
    val rules: List<TreeRule>
    val terminalRules: List<TreeTerminalRule>
    val keywordModel: XtextKeywordModel

    fun getParserRuleByName(name: String): TreeParserRule?

    fun getRuleByName(name: String): TreeRule

    fun findLiteralAssignmentsInRule(rule: TreeRule): List<TreeLeaf>

    fun findObjectAssignmentsInRule(rule: TreeRule): List<TreeRuleCall>


}