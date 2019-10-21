package com.intellij.xtextLanguage.xtext.generator.visitors

import com.intellij.xtextLanguage.xtext.psi.XtextAction
import com.intellij.xtextLanguage.xtext.psi.XtextConditionalBranch
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class XtextVisitorActions : XtextVisitorRuleCalls() {
    val mapOfBranchesWithActions = mutableMapOf<XtextConditionalBranch, XtextAction>()

    companion object {
        fun getBranchesWithActionsInParserRule(rule: XtextParserRule): Map<XtextConditionalBranch, XtextAction> {
            val visitor = XtextVisitorActions()
            visitor.visitParserRule(rule)
            return visitor.mapOfBranchesWithActions
        }
    }

    override fun visitConditionalBranch(o: XtextConditionalBranch) {
        val branch = o
        o.unorderedGroup?.groupList?.flatMap { it.abstractTokenList }?.forEach {
            it.action?.let {
                if (it.validID == null) {
                    mapOfBranchesWithActions.put(branch, it)
                }
            }
        }
    }
}