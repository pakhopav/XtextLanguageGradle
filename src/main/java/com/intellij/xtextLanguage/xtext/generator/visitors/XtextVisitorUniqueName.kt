package com.intellij.xtextLanguage.xtext.generator.visitors

import com.intellij.xtextLanguage.xtext.psi.XtextAlternatives
import com.intellij.xtextLanguage.xtext.psi.XtextAssignment
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class XtextVisitorUniqueName : XtextVisitorRuleCalls() {
    var uniqueName: String? = null
    var isNameAvailable = false

    companion object {
        fun getUniqueNameOfParserRule(rule: XtextParserRule): String? {
            val visitor = XtextVisitorUniqueName()
            visitor.visitParserRule(rule)
            return visitor.uniqueName
        }
    }


    override fun visitAlternatives(o: XtextAlternatives) {
        val parent = o.parent
        isNameAvailable = parent is XtextParserRule && o.conditionalBranchList.size == 1
        super.visitAlternatives(o)
    }

    override fun visitAssignment(o: XtextAssignment) {
        if (isNameAvailable && o.validID.text == "name" && o.assignableTerminal.ruleCall != null) {
            uniqueName = o.assignableTerminal.ruleCall?.referenceAbstractRuleRuleID?.text
            isNameAvailable = false
        }
    }

}