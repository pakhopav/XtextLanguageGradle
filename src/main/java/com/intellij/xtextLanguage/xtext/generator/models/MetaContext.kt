package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.*

interface MetaContext {
    val rules: List<TreeRule>
    val terminalRules: List<TerminalRule>
    val terminalRs: List<TreeTerminalRule>
    val keywordModel: XtextKeywordModel

    fun getParserRuleByName(name: String): TreeParserRule?

    fun getRuleByName(name: String): TreeRule

    fun getClassDescriptionByName(typeName: String): EmfClassDescriptor

    fun isReferencedRule(rule: TreeRule): Boolean

    fun isDuplicateRule(rule: TreeRule): Boolean

    fun hasPrivateDuplicate(rule: TreeRule): Boolean

    fun findLiteralAssignmentsInRule(rule: TreeRule): List<TreeLeaf>

    fun findObjectAssignmentsInRule(rule: TreeRule): List<TreeRuleCall>


}