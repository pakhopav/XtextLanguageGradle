package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.generator.models.elements.EnumRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRule

interface RuleResolver {
    fun getTerminalRuleByName(name: String): TerminalRule?
    fun getParserRuleByName(name: String): ParserRule?
    fun getEnumRuleByName(name: String): EnumRule?

}