package com.intellij.xtextLanguage.xtext.generator.visitors

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.RuleElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRegexpRangeElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRegexpReferenceRuleCallElelment
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRegexpSimpleElement
import com.intellij.xtextLanguage.xtext.psi.*

class XtextVisitorTerminalRules() : XtextVisitor() {
    val ruleElementsList = mutableListOf<RuleElement>()


    companion object {
        fun getRuleElementListOfTerminalRule(rule: XtextTerminalRule): List<RuleElement> {

            val visitor = XtextVisitorTerminalRules()
            visitor.visitTerminalRule(rule)
            return visitor.ruleElementsList
        }
    }

    override fun visitTerminalAlternatives(o: XtextTerminalAlternatives) {
        o.terminalGroupList.forEach {
            visitTerminalGroup(it)
            getPipePsiElementIfExists(it)?.let {
                ruleElementsList.add(TerminalRegexpSimpleElement(it))
            }
        }
    }

    override fun visitTerminalToken(o: XtextTerminalToken) {
        super.visitTerminalToken(o)
        o.plusKeyword?.let {
            ruleElementsList.add(TerminalRegexpSimpleElement(it))
        }
        o.quesMarkKeyword?.let {
            ruleElementsList.add(TerminalRegexpSimpleElement(it))
        }
        o.asteriskKeyword?.let {
            ruleElementsList.add(TerminalRegexpSimpleElement(it))
        }
    }

    override fun visitCharacterRange(o: XtextCharacterRange) {
        ruleElementsList.add(TerminalRegexpRangeElement(o))
    }

    override fun visitParenthesizedTerminalElement(o: XtextParenthesizedTerminalElement) {
        ruleElementsList.add(TerminalRegexpSimpleElement(o.lBracketKeyword))
        visitTerminalAlternatives(o.terminalAlternatives)
        ruleElementsList.add(TerminalRegexpSimpleElement(o.rBracketKeyword))

    }

    override fun visitWildcard(o: XtextWildcard) {
        ruleElementsList.add(TerminalRegexpSimpleElement(o))

    }

    override fun visitAbstractNegatedToken(o: XtextAbstractNegatedToken) {
        o.negatedToken?.let {

        }
        o.untilToken?.let {

        }
    }

    override fun visitTerminalRuleCall(o: XtextTerminalRuleCall) {
        ruleElementsList.add(TerminalRegexpReferenceRuleCallElelment(o))
    }

    fun getPipePsiElementIfExists(o: PsiElement): PsiElement? {
        o.nextSibling?.let {
            if (it.node.elementType == XtextTypes.PIPE_KEYWORD) {
                return it
            } else {
                it.nextSibling?.let {
                    if (it.node.elementType == XtextTypes.PIPE_KEYWORD) {
                        return it
                    }
                }
            }


        }
        return null
    }
}