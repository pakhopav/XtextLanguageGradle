package com.intellij.xtextLanguage.xtext.psi

class XtextNameVisitor {
//    fun visitGrammar(node: XtextGrammar): PsiElement? {
//        return node.grammarID
//    }
//
//    fun visitGrammarID(node: XtextGrammarID): PsiElement? {
//        node.validIDList.forEach { visitValidID(it)?.let { return@visitGrammarID it } }
//        return null
//    }
//
//    fun visitAbstractRule(node: XtextAbstractRule): PsiElement? {
//        node.parserRule?.let { visitParserRule(it)?.let { return@visitAbstractRule it } }
//        node.terminalRule?.let { visitTerminalRule(it)?.let { return@visitAbstractRule it } }
//        node.enumRule?.let { visitEnumRule(it)?.let { return@visitAbstractRule it } }
//        return null
//    }
//
//    fun visitAbstractMetamodelDeclaration(node: XtextAbstractMetamodelDeclaration): PsiElement? {
//        node.generatedMetamodel?.let { visitGeneratedMetamodel(it)?.let { return@visitAbstractMetamodelDeclaration it } }
//        node.referencedMetamodel?.let { visitReferencedMetamodel(it)?.let { return@visitAbstractMetamodelDeclaration it } }
//        return null
//    }
//
//    fun visitGeneratedMetamodel(node: XtextGeneratedMetamodel): PsiElement? {
//        node.validIDList.forEach { visitValidID(it)?.let { return@visitGeneratedMetamodel it } }
//        return null
//    }
//
//    fun visitReferencedMetamodel(node: XtextReferencedMetamodel): PsiElement? {
//        return null
//    }
//
//    fun visitAnnotation(node: XtextAnnotation): PsiElement? {
//        return node.id
//    }
//
//    fun visitParserRule(node: XtextParserRule): PsiElement? {
//        node.ruleNameAndParams?.let { visitRuleNameAndParams(it)?.let { return@visitParserRule it } }
//        node.alternatives?.let { visitAlternatives(it)?.let { return@visitParserRule it } }
//        return null
//    }
//
//    fun visitRuleNameAndParams(node: XtextRuleNameAndParams): PsiElement? {
//        return node.validID
//    }
//
//    fun visitParameter(node: XtextParameter): PsiElement? {
//        return node.id
//    }
//
//    fun visitTypeRef(node: XtextTypeRef): PsiElement? {
//        return null
//    }
//
//    fun visitAlternatives(node: XtextAlternatives): PsiElement? {
//        node.conditionalBranchList.forEach { visitConditionalBranch(it)?.let { return@visitAlternatives it } }
//        return null
//    }
//
//    fun visitRuleFromConditionalBranchBranch2(node: XtextRuleFromConditionalBranchBranch2): PsiElement? {
//        node.disjunction?.let { visitDisjunction(it)?.let { return@visitRuleFromConditionalBranchBranch2 it } }
//        return null
//    }
//
//    fun visitConditionalBranch(node: XtextConditionalBranch): PsiElement? {
//        node.unorderedGroup?.let { visitUnorderedGroup(it)?.let { return@visitConditionalBranch it } }
//        node.ruleFromConditionalBranchBranch2?.let { visitRuleFromConditionalBranchBranch2(it)?.let { return@visitConditionalBranch it } }
//        return null
//    }
//
//    fun visitUnorderedGroup(node: XtextUnorderedGroup): PsiElement? {
//        node.groupList.forEach { visitGroup(it)?.let { return@visitUnorderedGroup it } }
//        return null
//    }
//
//    fun visitGroup(node: XtextGroup): PsiElement? {
//        node.abstractTokenList.forEach { visitAbstractToken(it)?.let { return@visitGroup it } }
//        return null
//    }
//
//    fun visitAbstractToken(node: XtextAbstractToken): PsiElement? {
//        node.abstractTokenWithCardinality?.let { visitAbstractTokenWithCardinality(it)?.let { return@visitAbstractToken it } }
//        node.action?.let { visitAction(it)?.let { return@visitAbstractToken it } }
//        return null
//    }
//
//    fun visitAbstractTokenWithCardinality(node: XtextAbstractTokenWithCardinality): PsiElement? {
//        node.assignment?.let { visitAssignment(it)?.let { return@visitAbstractTokenWithCardinality it } }
//        node.abstractTerminal?.let { visitAbstractTerminal(it)?.let { return@visitAbstractTokenWithCardinality it } }
//        return null
//    }
//
//    fun visitAction(node: XtextAction): PsiElement? {
//        node.typeRef?.let { visitTypeRef(it)?.let { return@visitAction it } }
//        return null
//    }
//
//    fun visitAbstractTerminal(node: XtextAbstractTerminal): PsiElement? {
//        node.keyword?.let { visitKeyword(it)?.let { return@visitAbstractTerminal it } }
//        node.ruleCall?.let { visitRuleCall(it)?.let { return@visitAbstractTerminal it } }
//        node.parenthesizedElement?.let { visitParenthesizedElement(it)?.let { return@visitAbstractTerminal it } }
//        node.predicatedKeyword?.let { visitPredicatedKeyword(it)?.let { return@visitAbstractTerminal it } }
//        node.predicatedRuleCall?.let { visitPredicatedRuleCall(it)?.let { return@visitAbstractTerminal it } }
//        node.predicatedGroup?.let { visitPredicatedGroup(it)?.let { return@visitAbstractTerminal it } }
//        return null
//    }
//
//    fun visitKeyword(node: XtextKeyword): PsiElement? {
//        return null
//    }
//
//    fun visitRuleCall(node: XtextRuleCall): PsiElement? {
//        return null
//    }
//
//    fun visitNamedArgument(node: XtextNamedArgument): PsiElement? {
//        node.disjunction?.let { visitDisjunction(it)?.let { return@visitNamedArgument it } }
//        return null
//    }
//
//    fun visitRuleFromLiteralConditionBranch1(node: XtextRuleFromLiteralConditionBranch1): PsiElement? {
//        return null
//    }
//
//    fun visitLiteralCondition(node: XtextLiteralCondition): PsiElement? {
//        node.ruleFromLiteralConditionBranch1?.let { visitRuleFromLiteralConditionBranch1(it)?.let { return@visitLiteralCondition it } }
//        return null
//    }
//
//    fun visitDisjunction(node: XtextDisjunction): PsiElement? {
//        node.conjunctionList.forEach { visitConjunction(it)?.let { return@visitDisjunction it } }
//        return null
//    }
//
//    fun visitConjunction(node: XtextConjunction): PsiElement? {
//        node.negationList.forEach { visitNegation(it)?.let { return@visitConjunction it } }
//        return null
//    }
//
//    fun visitRuleFromNegationBranch2(node: XtextRuleFromNegationBranch2): PsiElement? {
//        node.negation?.let { visitNegation(it)?.let { return@visitRuleFromNegationBranch2 it } }
//        return null
//    }
//
//    fun visitNegation(node: XtextNegation): PsiElement? {
//        node.atom?.let { visitAtom(it)?.let { return@visitNegation it } }
//        node.ruleFromNegationBranch2?.let { visitRuleFromNegationBranch2(it)?.let { return@visitNegation it } }
//        return null
//    }
//
//    fun visitAtom(node: XtextAtom): PsiElement? {
//        node.parameterReference?.let { visitParameterReference(it)?.let { return@visitAtom it } }
//        node.parenthesizedCondition?.let { visitParenthesizedCondition(it)?.let { return@visitAtom it } }
//        node.literalCondition?.let { visitLiteralCondition(it)?.let { return@visitAtom it } }
//        return null
//    }
//
//    fun visitParenthesizedCondition(node: XtextParenthesizedCondition): PsiElement? {
//        node.disjunction?.let { visitDisjunction(it)?.let { return@visitParenthesizedCondition it } }
//        return null
//    }
//
//    fun visitParameterReference(node: XtextParameterReference): PsiElement? {
//        return null
//    }
//
//    fun visitTerminalRuleCall(node: XtextTerminalRuleCall): PsiElement? {
//        return null
//    }
//
//    fun visitRuleID(node: XtextRuleID): PsiElement? {
//        node.validIDList.forEach { visitValidID(it)?.let { return@visitRuleID it } }
//        return null
//    }
//
//    fun visitValidID(node: XtextValidID): PsiElement? {
//        node.id?.let { return@visitValidID it }
//        return null
//    }
//
//    fun visitPredicatedKeyword(node: XtextPredicatedKeyword): PsiElement? {
//        return null
//    }
//
//    fun visitPredicatedRuleCall(node: XtextPredicatedRuleCall): PsiElement? {
//        return null
//    }
//
//    fun visitAssignment(node: XtextAssignment): PsiElement? {
//        node.validID?.let { visitValidID(it)?.let { return@visitAssignment it } }
//        node.assignableTerminal?.let { visitAssignableTerminal(it)?.let { return@visitAssignment it } }
//        return null
//    }
//
//    fun visitAssignableTerminal(node: XtextAssignableTerminal): PsiElement? {
//        node.keyword?.let { visitKeyword(it)?.let { return@visitAssignableTerminal it } }
//        node.ruleCall?.let { visitRuleCall(it)?.let { return@visitAssignableTerminal it } }
//        node.parenthesizedAssignableElement?.let { visitParenthesizedAssignableElement(it)?.let { return@visitAssignableTerminal it } }
//        node.crossReference?.let { visitCrossReference(it)?.let { return@visitAssignableTerminal it } }
//        return null
//    }
//
//    fun visitParenthesizedAssignableElement(node: XtextParenthesizedAssignableElement): PsiElement? {
//        node.assignableAlternatives?.let { visitAssignableAlternatives(it)?.let { return@visitParenthesizedAssignableElement it } }
//        return null
//    }
//
//    fun visitAssignableAlternatives(node: XtextAssignableAlternatives): PsiElement? {
//        node.assignableTerminalList.forEach { visitAssignableTerminal(it)?.let { return@visitAssignableAlternatives it } }
//        return null
//    }
//
//    fun visitCrossReference(node: XtextCrossReference): PsiElement? {
//        node.typeRef?.let { visitTypeRef(it)?.let { return@visitCrossReference it } }
//        return null
//    }
//
//    fun visitCrossReferenceableTerminal(node: XtextCrossReferenceableTerminal): PsiElement? {
//        node.keyword?.let { visitKeyword(it)?.let { return@visitCrossReferenceableTerminal it } }
//        node.ruleCall?.let { visitRuleCall(it)?.let { return@visitCrossReferenceableTerminal it } }
//        return null
//    }
//
//    fun visitParenthesizedElement(node: XtextParenthesizedElement): PsiElement? {
//        node.alternatives?.let { visitAlternatives(it)?.let { return@visitParenthesizedElement it } }
//        return null
//    }
//
//    fun visitPredicatedGroup(node: XtextPredicatedGroup): PsiElement? {
//        node.alternatives?.let { visitAlternatives(it)?.let { return@visitPredicatedGroup it } }
//        return null
//    }
//
//    fun visitTerminalRule(node: XtextTerminalRule): PsiElement? {
//        node.validID?.let { visitValidID(it)?.let { return@visitTerminalRule it } }
//        node.terminalAlternatives?.let { visitTerminalAlternatives(it)?.let { return@visitTerminalRule it } }
//        return null
//    }
//
//    fun visitTerminalAlternatives(node: XtextTerminalAlternatives): PsiElement? {
//        node.terminalGroupList.forEach { visitTerminalGroup(it)?.let { return@visitTerminalAlternatives it } }
//        return null
//    }
//
//    fun visitTerminalGroup(node: XtextTerminalGroup): PsiElement? {
//        node.terminalTokenList.forEach { visitTerminalToken(it)?.let { return@visitTerminalGroup it } }
//        return null
//    }
//
//    fun visitTerminalToken(node: XtextTerminalToken): PsiElement? {
//        node.terminalTokenElement?.let { visitTerminalTokenElement(it)?.let { return@visitTerminalToken it } }
//        return null
//    }
//
//    fun visitTerminalTokenElement(node: XtextTerminalTokenElement): PsiElement? {
//        node.characterRange?.let { visitCharacterRange(it)?.let { return@visitTerminalTokenElement it } }
//        node.terminalRuleCall?.let { visitTerminalRuleCall(it)?.let { return@visitTerminalTokenElement it } }
//        node.parenthesizedTerminalElement?.let { visitParenthesizedTerminalElement(it)?.let { return@visitTerminalTokenElement it } }
//        node.abstractNegatedToken?.let { visitAbstractNegatedToken(it)?.let { return@visitTerminalTokenElement it } }
//        node.wildcard?.let { visitWildcard(it)?.let { return@visitTerminalTokenElement it } }
//        node.caretEOF?.let { visitCaretEOF(it)?.let { return@visitTerminalTokenElement it } }
//        return null
//    }
//
//    fun visitParenthesizedTerminalElement(node: XtextParenthesizedTerminalElement): PsiElement? {
//        node.terminalAlternatives?.let { visitTerminalAlternatives(it)?.let { return@visitParenthesizedTerminalElement it } }
//        return null
//    }
//
//    fun visitAbstractNegatedToken(node: XtextAbstractNegatedToken): PsiElement? {
//        node.negatedToken?.let { visitNegatedToken(it)?.let { return@visitAbstractNegatedToken it } }
//        node.untilToken?.let { visitUntilToken(it)?.let { return@visitAbstractNegatedToken it } }
//        return null
//    }
//
//    fun visitNegatedToken(node: XtextNegatedToken): PsiElement? {
//        node.terminalTokenElement?.let { visitTerminalTokenElement(it)?.let { return@visitNegatedToken it } }
//        return null
//    }
//
//    fun visitUntilToken(node: XtextUntilToken): PsiElement? {
//        node.terminalTokenElement?.let { visitTerminalTokenElement(it)?.let { return@visitUntilToken it } }
//        return null
//    }
//
//    fun visitRuleFromWildcardBranch1(node: XtextRuleFromWildcardBranch1): PsiElement? {
//        return null
//    }
//
//    fun visitWildcard(node: XtextWildcard): PsiElement? {
//        node.ruleFromWildcardBranch1?.let { visitRuleFromWildcardBranch1(it)?.let { return@visitWildcard it } }
//        return null
//    }
//
//    fun visitRuleFromCaretEOFBranch1(node: XtextRuleFromCaretEOFBranch1): PsiElement? {
//        return null
//    }
//
//    fun visitCaretEOF(node: XtextCaretEOF): PsiElement? {
//        node.ruleFromCaretEOFBranch1?.let { visitRuleFromCaretEOFBranch1(it)?.let { return@visitCaretEOF it } }
//        return null
//    }
//
//    fun visitCharacterRange(node: XtextCharacterRange): PsiElement? {
//        node.keywordList.forEach { visitKeyword(it)?.let { return@visitCharacterRange it } }
//        return null
//    }
//
//    fun visitEnumRule(node: XtextEnumRule): PsiElement? {
//        return node.validID
//    }
//
//    fun visitEnumLiterals(node: XtextEnumLiterals): PsiElement? {
//        node.enumLiteralDeclarationList.forEach { visitEnumLiteralDeclaration(it)?.let { return@visitEnumLiterals it } }
//        return null
//    }
//
//    fun visitEnumLiteralDeclaration(node: XtextEnumLiteralDeclaration): PsiElement? {
//        return null
//    }
}