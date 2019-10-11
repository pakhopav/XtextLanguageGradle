package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

interface VisitorGeneratorModel {
    val xtextRules: MutableList<XtextParserRule>

    var rules: MutableList<modelRule>

    fun getUniqueName(rule: modelRule): String?


    fun getXtextRuleName(rule: modelRule): String


    fun hasUniqueId(rule: modelRule): Boolean

    fun hasId(rule: modelRule): Boolean

    fun getRulesRuleCalls(rule: modelRule): List<String>

    fun getRulesRuleCallsList(rule: modelRule): List<String>

    class modelRule(val name: String) {
        var UniqueName: String? = null
        var UniqueId: Boolean = false
        var Id: Boolean = false
        var RuleCalls = mutableListOf<String>()
        var RuleCallsList = mutableListOf<String>()

    }

}