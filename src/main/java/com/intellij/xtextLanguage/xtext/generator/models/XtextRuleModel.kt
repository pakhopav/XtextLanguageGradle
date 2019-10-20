package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.RuleResolver
import com.intellij.xtextLanguage.xtext.generator.XtextTreeRefactor
import com.intellij.xtextLanguage.xtext.generator.models.elements.EnumRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRegexpReferenceRuleCallElelment
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRule
import com.intellij.xtextLanguage.xtext.psi.XtextEnumRule
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule
import com.intellij.xtextLanguage.xtext.psi.XtextTerminalRule

class XtextRuleModel(val xtextFile: XtextFile) {
    val xtextParserRules: List<XtextParserRule>
    val refactoredXtextParserRules: List<XtextParserRule>
    val parserRules: List<ParserRule>
    val enumRules: List<EnumRule>
    val terminalRules: List<TerminalRule>


    init {
        xtextParserRules = PsiTreeUtil.findChildrenOfType(xtextFile, XtextParserRule::class.java).toList()

        val refactor = XtextTreeRefactor()
        refactoredXtextParserRules = refactor.getRefactoredXtextRules(xtextParserRules)

        parserRules = refactoredXtextParserRules
                .map { ParserRule(it) }
        enumRules = PsiTreeUtil.findChildrenOfType(xtextFile, XtextEnumRule::class.java)
                .map { EnumRule(it) }
        terminalRules = PsiTreeUtil.findChildrenOfType(xtextFile, XtextTerminalRule::class.java)
                .map { TerminalRule(it) }
    }


    fun getTerminalRuleRegexp(rule: TerminalRule, resolver: RuleResolver): String {
        val sb = StringBuilder()
        rule.alterntivesElements.forEach {
            if (it is TerminalRegexpReferenceRuleCallElelment) {
                resolver.getTerminalRuleByName(it.getBnfName())?.let {
                    sb.append(getTerminalRuleRegexp(it, resolver))
                }
            } else {
                sb.append(it.getBnfName())
            }
        }
        return sb.toString()
    }
}