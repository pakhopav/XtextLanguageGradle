package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.generator.models.XtextRuleModel
import com.intellij.xtextLanguage.xtext.generator.models.elements.EnumRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRule

class RuleResolverImpl(val model: XtextRuleModel) : RuleResolver {
    override fun getParserRuleByName(name: String): ParserRule? {
        return model.parserRules.filter { it.name == name }.ifEmpty { null }?.first()
    }

    override fun getEnumRuleByName(name: String): EnumRule? {
        return model.enumRules.filter { it.name == name }.ifEmpty { null }?.first()
    }

    override fun getTerminalRuleByName(name: String): TerminalRule? {
        return model.terminalRules.filter { it.name == name }.ifEmpty { null }?.first()
    }
}