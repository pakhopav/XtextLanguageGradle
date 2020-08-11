package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.BridgeModelRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule

class BridgeModel(parserRules: List<ParserRule>) {
    val bridgeRules: List<BridgeModelRule>

    init {
        bridgeRules = createBridgeRulesFromParserRuleList(parserRules)
    }

    private fun createBridgeRulesFromParserRuleList(parserRules: List<ParserRule>): List<BridgeModelRule> {
        val list = mutableListOf<BridgeModelRule>()
        parserRules.forEach {

        }
        return list
    }
}