package com.intellij.xtextLanguage.xtext.generator.visitors

import com.intellij.xtextLanguage.xtext.psi.XtextParserRule
import com.intellij.xtextLanguage.xtext.psi.XtextRuleCall
import com.intellij.xtextLanguage.xtext.psi.XtextUnorderedGroup

class XtextVisitorRepeatingRuleCalls(val ruleCall: String) : XtextVisitorRuleCalls() {
    var isRepeating = false
    var timesSeen = 0


    companion object {
        fun isRuleCallRepeatsInBranchOfParserRule(rule: XtextParserRule, ruleCall: String): Boolean {
            val visitor = XtextVisitorRepeatingRuleCalls(ruleCall)
            visitor.visitParserRule(rule)
            return visitor.isRepeating
        }
    }


    override fun visitRuleCall(o: XtextRuleCall) {
        if (o.referenceAbstractRuleRuleID.text == ruleCall) {
            timesSeen++
        }
    }

    override fun visitUnorderedGroup(o: XtextUnorderedGroup) {
        var numberOfRepetitions = 0
        o.groupList.flatMap { it.abstractTokenList }.forEach {
            val seenBeforeToken = timesSeen
            visitAbstractToken(it)
            if (timesSeen > seenBeforeToken) numberOfRepetitions++
            if (numberOfRepetitions >= 2) {
                isRepeating = true
                return
            }
        }

    }


//    override fun visitGroup(o: XtextGroup) {
//        var  numberOfRepetitions = 0
//        o.abstractTokenList.forEach {
//            visitAbstractToken(it)
//            if(seen) numberOfRepetitions ++
//            if(numberOfRepetitions >= 2) {
//                isRepeating = true
//                return
//            }
//            seen = false
//        }
//    }
}