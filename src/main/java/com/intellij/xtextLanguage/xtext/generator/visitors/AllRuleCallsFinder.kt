package com.intellij.xtextLanguage.xtext.generator.visitors

import com.intellij.xtextLanguage.xtext.psi.XtextAbstractTokenWithCardinality
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule
import com.intellij.xtextLanguage.xtext.psi.XtextRuleCall

class AllRuleCallsFinder(val crossReferencesNames: List<String>) : XtextVisitorRuleCalls() {
    val ruleCalls = mutableListOf<String>()

    fun getAllRuleCallsInParserRule(rule: XtextParserRule): MutableList<String> {
        ruleCalls.clear()
        visitParserRule(rule)
        return ruleCalls
    }


    override fun visitRuleCall(o: XtextRuleCall) {
        if (crossReferencesNames.contains(o.referenceAbstractRuleRuleID.text)) return
        ruleCalls.add(o.referenceAbstractRuleRuleID.text)
    }

    override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
        if (o.quesMarkKeyword == null && o.asteriskKeyword == null && o.plusKeyword == null) {
            super.visitAbstractTokenWithCardinality(o)

        }
    }
}