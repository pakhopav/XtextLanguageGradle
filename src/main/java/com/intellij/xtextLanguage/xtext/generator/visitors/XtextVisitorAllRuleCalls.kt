package com.intellij.xtextLanguage.xtext.generator.visitors

import com.intellij.xtextLanguage.xtext.psi.XtextAbstractTokenWithCardinality
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule
import com.intellij.xtextLanguage.xtext.psi.XtextRuleCall

class XtextVisitorAllRuleCalls : XtextVisitorRuleCalls() {
    val RuleCalls = mutableListOf<String>()


    companion object {
        fun getAllRuleCallsInParserRule(rule: XtextParserRule): MutableList<String> {
            val visitor = XtextVisitorAllRuleCalls()
            visitor.visitParserRule(rule)
            return visitor.RuleCalls
        }
    }

    override fun visitRuleCall(o: XtextRuleCall) {
        RuleCalls.add(o.referenceAbstractRuleRuleID.text)
    }

    override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
        if (o.quesMarkKeyword == null && o.asteriskKeyword == null && o.plusKeyword == null) {
            super.visitAbstractTokenWithCardinality(o)

        }
    }
}