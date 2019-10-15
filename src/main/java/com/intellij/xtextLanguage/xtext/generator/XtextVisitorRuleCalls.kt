package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.*

open class XtextVisitorRuleCalls {
    open fun visitAbstractMetamodelDeclaration(o: XtextAbstractMetamodelDeclaration) {
    }

    open fun visitAbstractNegatedToken(o: XtextAbstractNegatedToken) {
    }

    open fun visitAbstractRule(o: XtextAbstractRule) {
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

    open fun visitAbstractTerminal(o: XtextAbstractTerminal) {
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

    open fun visitAbstractToken(o: XtextAbstractToken) {
        o.abstractTokenWithCardinality?.let {
            visitAbstractTokenWithCardinality(it)
        }
    }

    open fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
        o.abstractTerminal?.let {
            visitAbstractTerminal(it)
        }
        o.assignment?.let {
            visitAssignment(it)
        }
    }

    open fun visitAction(o: XtextAction) {
    }

    open fun visitAlternatives(o: XtextAlternatives) {
        o.conditionalBranchList.forEach {
            visitConditionalBranch(it)
        }
    }

    open fun visitAnnotation(o: XtextAnnotation) {
    }

    open fun visitAssignableAlternatives(o: XtextAssignableAlternatives) {
        o.assignableTerminalList.forEach {
            visitAssignableTerminal(it)
        }
    }

    open fun visitAssignableTerminal(o: XtextAssignableTerminal) {
        o.ruleCall?.let {
            visitRuleCall(it)
        }
        o.parenthesizedAssignableElement?.let {
            visitParenthesizedAssignableElement(it)
        }
    }

    open fun visitAssignment(o: XtextAssignment) {
        visitAssignableTerminal(o.assignableTerminal)
    }

    open fun visitAtom(o: XtextAtom) {
    }

    open fun visitCaretEOF(o: XtextCaretEOF) {
    }

    open fun visitCharacterRange(o: XtextCharacterRange) {
    }

    open fun visitConditionalBranch(o: XtextConditionalBranch) {
        o.unorderedGroup?.let {
            visitUnorderedGroup(it)
        }
    }

    open fun visitConjunction(o: XtextConjunction) {
    }

    open fun visitCrossReference(o: XtextCrossReference) {
    }

    open fun visitCrossReferenceableTerminal(o: XtextCrossReferenceableTerminal) {
    }

    open fun visitDisjunction(o: XtextDisjunction) {
    }

    open fun visitEnumLiteralDeclaration(o: XtextEnumLiteralDeclaration) {
    }

    open fun visitEnumLiterals(o: XtextEnumLiterals) {
    }

    open fun visitEnumRule(o: XtextEnumRule) {
    }

    open fun visitGeneratedMetamodel(o: XtextGeneratedMetamodel) {
    }

    open fun visitGrammar(o: XtextGrammar) {
        o.abstractRuleList.forEach {
            visitAbstractRule(it)
        }
    }

    open fun visitGrammarID(o: XtextGrammarID) {
    }

    open fun visitGroup(o: XtextGroup) {
        o.abstractTokenList.forEach {
            visitAbstractToken(it)
        }
    }

    open fun visitKeyword(o: XtextKeyword) {
    }

    open fun visitLiteralCondition(o: XtextLiteralCondition) {
    }

    open fun visitNamedArgument(o: XtextNamedArgument) {
    }

    open fun visitNegatedToken(o: XtextNegatedToken) {
    }

    open fun visitNegation(o: XtextNegation) {
    }

    open fun visitParameter(o: XtextParameter) {
    }

    open fun visitParameterReference(o: XtextParameterReference) {
    }

    open fun visitParenthesizedAssignableElement(o: XtextParenthesizedAssignableElement) {
        visitAssignableAlternatives(o.assignableAlternatives)
    }

    open fun visitParenthesizedCondition(o: XtextParenthesizedCondition) {
    }

    open fun visitParenthesizedElement(o: XtextParenthesizedElement) {
        visitAlternatives(o.alternatives)
    }

    open fun visitParenthesizedTerminalElement(o: XtextParenthesizedTerminalElement) {
    }

    open fun visitParserRule(o: XtextParserRule) {
        visitAlternatives(o.alternatives)
    }

    open fun visitPredicatedGroup(o: XtextPredicatedGroup) {
        visitAlternatives(o.alternatives)
    }

    open fun visitPredicatedKeyword(o: XtextPredicatedKeyword) {
    }

    open fun visitPredicatedRuleCall(o: XtextPredicatedRuleCall) {
    }

    open fun visitREFERENCEAbstractMetamodelDeclaration(o: XtextREFERENCEAbstractMetamodelDeclaration) {
    }

    open fun visitREFERENCEAbstractRuleRuleID(o: XtextREFERENCEAbstractRuleRuleID) {
    }

    open fun visitREFERENCEGrammarGrammarID(o: XtextREFERENCEGrammarGrammarID) {
    }

    open fun visitREFERENCEParameterID(o: XtextREFERENCEParameterID) {
    }

    open fun visitREFERENCEEcoreEClassifier(o: XtextREFERENCEEcoreEClassifier) {
    }

    open fun visitREFERENCEEcoreEEnumLiteral(o: XtextREFERENCEEcoreEEnumLiteral) {
    }

    open fun visitREFERENCEEcoreEPackageSTRING(o: XtextREFERENCEEcoreEPackageSTRING) {
    }

    open fun visitReferencedMetamodel(o: XtextReferencedMetamodel) {
    }

    open fun visitRuleCall(o: XtextRuleCall) {
    }

    open fun visitRuleFromCaretEOFBranch1(o: XtextRuleFromCaretEOFBranch1) {
    }

    open fun visitRuleFromConditionalBranchBranch2(o: XtextRuleFromConditionalBranchBranch2) {
    }

    open fun visitRuleFromLiteralConditionBranch1(o: XtextRuleFromLiteralConditionBranch1) {
    }

    open fun visitRuleFromNegationBranch2(o: XtextRuleFromNegationBranch2) {
    }

    open fun visitRuleFromWildcardBranch1(o: XtextRuleFromWildcardBranch1) {
    }

    open fun visitRuleID(o: XtextRuleID) {
    }

    open fun visitRuleNameAndParams(o: XtextRuleNameAndParams) {
    }

    open fun visitTerminalAlternatives(o: XtextTerminalAlternatives) {
    }

    open fun visitTerminalGroup(o: XtextTerminalGroup) {
    }

    open fun visitTerminalRule(o: XtextTerminalRule) {
    }

    open fun visitTerminalRuleCall(o: XtextTerminalRuleCall) {
    }

    open fun visitTerminalToken(o: XtextTerminalToken) {
    }

    open fun visitTerminalTokenElement(o: XtextTerminalTokenElement) {
    }

    open fun visitTypeRef(o: XtextTypeRef) {
    }

    open fun visitUnorderedGroup(o: XtextUnorderedGroup) {
        o.groupList.forEach {
            visitGroup(it)
        }
    }

    open fun visitUntilToken(o: XtextUntilToken) {
    }

    open fun visitValidID(o: XtextValidID) {
    }

    open fun visitWildcard(o: XtextWildcard) {
    }


}