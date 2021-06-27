package com.intellij.xtextLanguage.xtext.generator.models
import com.intellij.xtextLanguage.xtext.psi.*

open class XtextVisitor {
    open fun visitAbstractMetamodelDeclaration(o: XtextAbstractMetamodelDeclaration) {
    }

    open fun visitAbstractNegatedToken(abstractNegatedToken: XtextAbstractNegatedToken) {
        abstractNegatedToken.negatedToken?.let {
            visitNegatedToken(it)
        }
        abstractNegatedToken.untilToken?.let {
            visitUntilToken(it)
        }
    }

    open fun visitAbstractRule(abstractRule: XtextAbstractRule) {
        if (abstractRule is XtextParserRule) {
            visitParserRule(abstractRule)

        } else if (abstractRule is XtextEnumRule) {
            visitEnumRule(abstractRule)

        } else if (abstractRule is XtextTerminalRule) {
            visitTerminalRule(abstractRule)
        }
    }

    open fun visitAbstractTerminal(abstractTerminal: XtextAbstractTerminal) {
        abstractTerminal.keyword?.let {
            visitKeyword(it)
        }
        abstractTerminal.ruleCall?.let {
            visitRuleCall(it)
        }
        abstractTerminal.parenthesizedElement?.let {
            visitParenthesizedElement(it)
        }
        abstractTerminal.predicatedKeyword?.let {
            visitPredicatedKeyword(it)
        }
        abstractTerminal.predicatedRuleCall?.let {
            visitPredicatedRuleCall(it)
        }
        abstractTerminal.predicatedGroup?.let {
            visitPredicatedGroup(it)
        }
    }

    open fun visitAbstractToken(abstractToken: XtextAbstractToken) {
        abstractToken.action?.let {
            visitAction(it)
        }
        abstractToken.abstractTokenWithCardinality?.let {
            visitAbstractTokenWithCardinality(it)
        }
    }

    open fun visitAbstractTokenWithCardinality(abstractTokenWithCardinality: XtextAbstractTokenWithCardinality) {
        abstractTokenWithCardinality.abstractTerminal?.let {
            visitAbstractTerminal(it)
        }
        abstractTokenWithCardinality.assignment?.let {
            visitAssignment(it)
        }

    }

    open fun visitAction(action: XtextAction) {
    }

    open fun visitAlternatives(alternatives: XtextAlternatives) {
        visitConditionalBranch(alternatives.conditionalBranch)
        alternatives.alternativesSuffix1?.let {
            it.conditionalBranchList.forEach {
                visitConditionalBranch(it)
            }
        }
    }

    open fun visitAnnotation(annotation: XtextAnnotation) {

    }

    open fun visitAssignableAlternatives(assignableAlternatives: XtextAssignableAlternatives) {
        visitAssignableTerminal(assignableAlternatives.assignableTerminal)
        assignableAlternatives.assignableAlternativesSuffix1?.let {
            it.assignableTerminalList.forEach {
                visitAssignableTerminal(it)
            }
        }
    }


    open fun visitAssignableTerminal(assignableTerminal: XtextAssignableTerminal) {
        assignableTerminal.keyword?.let {
            visitKeyword(it)
        }
        assignableTerminal.ruleCall?.let {
            visitRuleCall(it)
        }
        assignableTerminal.parenthesizedAssignableElement?.let {
            visitParenthesizedAssignableElement(it)
        }
        assignableTerminal.crossReference?.let {
            visitCrossReference(it)
        }
    }

    open fun visitAssignment(assignment: XtextAssignment) {
        visitValidID(assignment.validID)
        visitAssignableTerminal(assignment.assignableTerminal)
    }

    open fun visitAtom(o: XtextAtom) {
    }

    open fun visitCharacterRange(characterRange: XtextCharacterRange) {
    }

    open fun visitConditionalBranch(conditionalBranch: XtextConditionalBranch) {
        conditionalBranch.unorderedGroup?.let {
            visitUnorderedGroup(it)
        }
        conditionalBranch.disjunction?.let {
            visitDisjunction(it)
        }
        conditionalBranch.abstractTokenList.forEach {
            visitAbstractToken(it)
        }
    }

    open fun visitConjunction(o: XtextConjunction) {
    }

    open fun visitCrossReference(crossReference: XtextCrossReference) {
        visitTypeRef(crossReference.typeRef)
        crossReference.crossReferenceableTerminal?.let {
            visitCrossReferenceableTerminal(it)
        }
    }

    open fun visitCrossReferenceableTerminal(crossReferenceableTerminal: XtextCrossReferenceableTerminal) {
        crossReferenceableTerminal.keyword?.let {
            visitKeyword(it)
        }
        crossReferenceableTerminal.ruleCall?.let {
            visitRuleCall(it)
        }
    }

    open fun visitDisjunction(disjunction: XtextDisjunction) {
    }

    open fun visitEnumLiteralDeclaration(enumLiteralDeclaration: XtextEnumLiteralDeclaration) {
    }

    open fun visitEnumLiterals(enumLiterals: XtextEnumLiterals) {
        visitEnumLiteralDeclaration(enumLiterals.enumLiteralDeclaration)
        enumLiterals.enumLiteralsSuffix1?.let {
            it.enumLiteralDeclarationList.forEach {
                visitEnumLiteralDeclaration(it)
            }
        }
    }

    open fun visitEnumRule(enumRule: XtextEnumRule) {
        visitValidID(enumRule.validID)
        visitEnumLiterals(enumRule.enumLiterals)
    }

    open fun visitGeneratedMetamodel(o: XtextGeneratedMetamodel) {

    }

    open fun visitGrammar(o: XtextGrammar) {
        visitGrammarID(o.grammarID)
        o.referenceGrammarGrammarIDList.forEach {
            visitREFERENCEGrammarGrammarID(it)
        }
        o.referenceAbstractRuleRuleIDList.forEach {
            visitREFERENCEAbstractRuleRuleID(it)
        }
        o.abstractMetamodelDeclarationList.forEach {
            visitAbstractMetamodelDeclaration(it)
        }
        o.abstractRuleList.forEach {
            visitAbstractRule(it)
        }
    }

    open fun visitGrammarID(o: XtextGrammarID) {
        o.validIDList.forEach {
            visitValidID(it)
        }
    }

    open fun visitGroup(group: XtextGroup) {
        visitAbstractToken(group.abstractToken)
        group.groupSuffix1?.let {
            it.abstractTokenList.forEach {
                visitAbstractToken(it)
            }
        }
    }

    open fun visitKeyword(keyword: XtextKeyword) {
    }

    open fun visitLiteralCondition(o: XtextLiteralCondition) {

    }

    open fun visitNamedArgument(o: XtextNamedArgument) {
    }

    open fun visitNegatedToken(negatedToken: XtextNegatedToken) {
    }

    open fun visitNegation(o: XtextNegation) {
    }

    open fun visitParameter(o: XtextParameter) {
    }

    open fun visitParameterReference(o: XtextParameterReference) {
    }

    open fun visitParenthesizedAssignableElement(assignableElement: XtextParenthesizedAssignableElement) {
        visitAssignableAlternatives(assignableElement.assignableAlternatives)
    }

    open fun visitParenthesizedCondition(o: XtextParenthesizedCondition) {
    }

    open fun visitParenthesizedElement(parenthesizedElement: XtextParenthesizedElement) {
        visitAlternatives(parenthesizedElement.alternatives)

    }

    open fun visitParenthesizedTerminalElement(parenthesizedTerminalElement: XtextParenthesizedTerminalElement) {
    }

    open fun visitParserRule(parserRule: XtextParserRule) {

        visitValidID(parserRule.validID)
        visitAlternatives(parserRule.alternatives)
    }

    open fun visitPredicatedGroup(predicatedGroup: XtextPredicatedGroup) {
        visitAlternatives(predicatedGroup.alternatives)
    }

    open fun visitPredicatedKeyword(predicatedKeyword: XtextPredicatedKeyword) {

    }

    open fun visitPredicatedRuleCall(predicatedRuleCall: XtextPredicatedRuleCall) {
        visitREFERENCEAbstractRuleRuleID(predicatedRuleCall.referenceAbstractRuleRuleID)
        predicatedRuleCall.namedArgumentList.forEach {
            visitNamedArgument(it)
        }
    }


    open fun visitREFERENCEAbstractRuleRuleID(ruleID: XtextREFERENCEAbstractRuleRuleID) {
        visitRuleID(ruleID.ruleID)
    }

    open fun visitREFERENCEGrammarGrammarID(o: XtextREFERENCEGrammarGrammarID) {
        visitGrammarID(o.grammarID)
    }

    open fun visitREFERENCEParameterID(o: XtextREFERENCEParameterID) {
    }


    open fun visitReferencedMetamodel(o: XtextReferencedMetamodel) {
    }

    open fun visitRuleCall(ruleCall: XtextRuleCall) {
        visitREFERENCEAbstractRuleRuleID(ruleCall.referenceAbstractRuleRuleID)
    }


    open fun visitRuleID(o: XtextRuleID) {
        o.validIDList.forEach {
            visitValidID(it)
        }
    }


    open fun visitTerminalAlternatives(terminalAlternatives: XtextTerminalAlternatives) {
        visitTerminalGroup(terminalAlternatives.terminalGroup)
        terminalAlternatives.terminalAlternativesSuffix1?.let {
            it.terminalGroupList.forEach {
                visitTerminalGroup(it)
            }
        }
    }

    open fun visitTerminalGroup(terminalGroup: XtextTerminalGroup) {
        visitTerminalToken(terminalGroup.terminalToken)
        terminalGroup.terminalGroupSuffix1?.let {
            it.terminalTokenList.forEach {
                visitTerminalToken(it)
            }
        }
    }

    open fun visitTerminalRule(o: XtextTerminalRule) {
        o.annotationList.forEach {
            visitAnnotation(it)
        }
        visitValidID(o.validID)
        o.typeRef?.let {
            visitTypeRef(it)
        }
        visitTerminalAlternatives(o.terminalAlternatives)
    }

    open fun visitTerminalRuleCall(ruleCall: XtextTerminalRuleCall) {
    }

    open fun visitTerminalToken(terminalToken: XtextTerminalToken) {
        visitTerminalTokenElement(terminalToken.terminalTokenElement)
    }

    open fun visitTerminalTokenElement(terminalTokenElement: XtextTerminalTokenElement) {
        terminalTokenElement.characterRange?.let {
            visitCharacterRange(it)
        }
        terminalTokenElement.terminalRuleCall?.let {
            visitTerminalRuleCall(it)
        }
        terminalTokenElement.parenthesizedTerminalElement?.let {
            visitParenthesizedTerminalElement(it)
        }
        terminalTokenElement.abstractNegatedToken?.let {
            visitAbstractNegatedToken(it)
        }
        terminalTokenElement.wildcard?.let {
            visitWildcard(it)
        }
    }

    open fun visitTypeRef(typeRef: XtextTypeRef) {
    }

    open fun visitUnorderedGroup(unorderedGroup: XtextUnorderedGroup) {
        visitGroup(unorderedGroup.group)
        unorderedGroup.unorderedGroupSuffix1?.let {
            it.groupList.forEach {
                visitGroup(it)
            }
        }

    }

    open fun visitUntilToken(untilToken: XtextUntilToken) {
    }

    open fun visitValidID(validID: XtextValidID) {

    }

    open fun visitWildcard(wildcard: XtextWildcard) {
    }
}