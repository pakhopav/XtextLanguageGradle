package com.intellij.xtextLanguage.xtext.generator.visitors

import com.intellij.xtextLanguage.xtext.psi.XtextAssignableTerminal
import com.intellij.xtextLanguage.xtext.psi.XtextAssignment
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class RuleAttributesFinder : XtextVisitor() {
    val attributes: MutableMap<String, XtextAssignableTerminal> = HashMap()

    companion object {
        fun getAttributeMapOfParserRule(rule: XtextParserRule): Map<String, XtextAssignableTerminal> {

            val visitor = RuleAttributesFinder()
            visitor.visitParserRule(rule)
            return visitor.attributes
        }
    }

    override fun visitAssignment(o: XtextAssignment) {
        attributes.put(o.validID.text, o.assignableTerminal)
    }


}