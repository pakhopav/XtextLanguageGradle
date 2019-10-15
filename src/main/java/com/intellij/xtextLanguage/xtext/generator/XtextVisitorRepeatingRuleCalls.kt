package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.XtextRuleCall
import com.intellij.xtextLanguage.xtext.psi.XtextUnorderedGroup

class XtextVisitorRepeatingRuleCalls(val ruleCall: String) : XtextVisitorRuleCalls() {
    var isRepeating = false
    var seen = false

    override fun visitRuleCall(o: XtextRuleCall) {
        if (o.referenceAbstractRuleRuleID.text == ruleCall) {
            seen = true
        }
    }

    override fun visitUnorderedGroup(o: XtextUnorderedGroup) {
        var numberOfRepetitions = 0
        o.groupList.flatMap { it.abstractTokenList }.forEach {
            visitAbstractToken(it)
            if (seen) numberOfRepetitions++
            if (numberOfRepetitions >= 2) {
                isRepeating = true
                return
            }
            seen = false
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