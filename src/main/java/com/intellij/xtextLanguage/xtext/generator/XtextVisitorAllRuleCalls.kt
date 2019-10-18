package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.XtextAbstractTokenWithCardinality
import com.intellij.xtextLanguage.xtext.psi.XtextRuleCall

class XtextVisitorAllRuleCalls : XtextVisitorRuleCalls() {
    val RuleCalls = mutableListOf<String>()
    override fun visitRuleCall(o: XtextRuleCall) {
        RuleCalls.add(o.referenceAbstractRuleRuleID.text)
    }

    override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
        if (o.quesMarkKeyword == null && o.asteriskKeyword == null && o.plusKeyword == null) {
            super.visitAbstractTokenWithCardinality(o)

        }
    }
}