package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.EnumRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.EmfClassDescriptor
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRoot
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRuleCall

interface MetaContext {
    val rules: List<TreeRoot>
    val terminalRules: List<TerminalRule>
    val enumRules: List<EnumRule>
    val keywordModel: XtextKeywordModel

    fun getParserRuleByName(name: String): TreeParserRule?

    fun getClassDescriptionByName(typeName: String): EmfClassDescriptor

//    fun getRuleReturnType(rule: TreeParserRule): EmfClassDescriptor

    fun isReferencedRule(rule: TreeRoot): Boolean

    fun isDuplicateRule(rule: TreeRoot): Boolean

    fun hasPrivateDuplicate(rule: TreeRoot): Boolean

    fun findLiteralAssignmentsInRule(rule: TreeRoot): List<TreeLeaf>

    fun findObjectAssignmentsInRule(rule: TreeRoot): List<TreeRuleCall>

}