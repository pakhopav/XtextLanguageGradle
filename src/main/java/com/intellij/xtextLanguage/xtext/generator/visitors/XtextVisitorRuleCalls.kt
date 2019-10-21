package com.intellij.xtextLanguage.xtext.generator.visitors

import com.intellij.xtextLanguage.xtext.psi.*

open class XtextVisitorRuleCalls : XtextVisitor() {
    override fun visitAbstractMetamodelDeclaration(o: XtextAbstractMetamodelDeclaration) {
    }


    override fun visitAbstractRule(o: XtextAbstractRule) {
        o.parserRule?.let {
            visitParserRule(it)
        }
        o.enumRule?.let {
            visitEnumRule(it)
        }
        o.terminalRule?.let {
            visitTerminalRule(it)
        }

    }

    override fun visitAbstractTerminal(o: XtextAbstractTerminal) {
        o.ruleCall?.let {
            visitRuleCall(it)
        }
        o.predicatedRuleCall?.let {
            visitPredicatedRuleCall(it)
        }
        o.parenthesizedElement?.let {
            visitParenthesizedElement(it)
        }
        o.predicatedGroup?.let {
            visitPredicatedGroup(it)
        }
    }

    override fun visitAbstractToken(o: XtextAbstractToken) {
        o.abstractTokenWithCardinality?.let {
            visitAbstractTokenWithCardinality(it)
        }
    }

    override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
        o.abstractTerminal?.let {
            visitAbstractTerminal(it)
        }
        o.assignment?.let {
            visitAssignment(it)
        }
    }


    override fun visitAlternatives(o: XtextAlternatives) {
        o.conditionalBranchList.forEach {
            visitConditionalBranch(it)
        }
    }


    override fun visitAssignableAlternatives(o: XtextAssignableAlternatives) {
        o.assignableTerminalList.forEach {
            visitAssignableTerminal(it)
        }
    }

    override fun visitAssignableTerminal(o: XtextAssignableTerminal) {
        o.ruleCall?.let {
            visitRuleCall(it)
        }
        o.parenthesizedAssignableElement?.let {
            visitParenthesizedAssignableElement(it)
        }
    }

    override fun visitAssignment(o: XtextAssignment) {
        visitAssignableTerminal(o.assignableTerminal)
    }


    override fun visitConditionalBranch(o: XtextConditionalBranch) {
        o.unorderedGroup?.let {
            visitUnorderedGroup(it)
        }
    }


    override fun visitGrammar(o: XtextGrammar) {
        o.abstractRuleList.forEach {
            visitAbstractRule(it)
        }
    }


    override fun visitGroup(o: XtextGroup) {
        o.abstractTokenList.forEach {
            visitAbstractToken(it)
        }
    }


    override fun visitParenthesizedAssignableElement(o: XtextParenthesizedAssignableElement) {
        visitAssignableAlternatives(o.assignableAlternatives)
    }


    override fun visitParenthesizedElement(o: XtextParenthesizedElement) {
        visitAlternatives(o.alternatives)
    }


    override fun visitParserRule(o: XtextParserRule) {
        visitAlternatives(o.alternatives)
    }

    override fun visitPredicatedGroup(o: XtextPredicatedGroup) {
        visitAlternatives(o.alternatives)
    }


    override fun visitUnorderedGroup(o: XtextUnorderedGroup) {
        o.groupList.forEach {
            visitGroup(it)
        }
    }


}