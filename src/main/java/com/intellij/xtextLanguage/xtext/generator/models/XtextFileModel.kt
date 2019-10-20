package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.RuleResolver
import com.intellij.xtextLanguage.xtext.generator.RuleResolverImpl
import com.intellij.xtextLanguage.xtext.generator.VisitorGeneratorModelImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule
import com.intellij.xtextLanguage.xtext.psi.XtextFile

class XtextFileModel(xtextFile: XtextFile) {
    val ruleModel: XtextRuleModel
    val referencesModel: XtextCrossReferencesModel
    val keywordModel: XtextKeywordModel
    lateinit var visitorGeneratorModel: VisitorGeneratorModel
    val ruleResolver: RuleResolver


    init {
        ruleModel = XtextRuleModel(xtextFile)
        referencesModel = XtextCrossReferencesModel(ruleModel)
        keywordModel = XtextKeywordModel(xtextFile)
        ruleResolver = RuleResolverImpl(ruleModel)

    }

    fun getNamedRules(): List<ParserRule> {
        val list = mutableListOf<ParserRule>()
        referencesModel.references.forEach {
            list.addAll(it.targets)
        }
        return list.distinct()
    }

    fun importGrammar(newModel: XtextFileModel) {
    }

    fun createVisitorGeneratorModel() {
        val terminalRulesNames = ruleModel.terminalRules.map { it.name }
        visitorGeneratorModel = VisitorGeneratorModelImpl(ruleModel.refactoredXtextParserRules, terminalRulesNames)

    }
}