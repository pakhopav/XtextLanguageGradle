package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.generator.RuleResolver
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*

class TerminalRule(val myRule: XtextTerminalRule, val resolver: RuleResolver) {
    val name = myRule.validID.text.toUpperCase()
    var isFragment: Boolean = myRule.fragmentKeyword != null
    val returnType: String = myRule.typeRef?.text ?: name
    val alterntiveElements = AlternativeElementsFinder.getRuleElementListOfTerminalRule(myRule, resolver)


    class AlternativeElementsFinder(val resolver: RuleResolver) : XtextVisitor() {
        val ruleElementsList = mutableListOf<TerminalRuleElement>()


        companion object {
            fun getRuleElementListOfTerminalRule(rule: XtextTerminalRule, resolver: RuleResolver): List<TerminalRuleElement> {

                val visitor = AlternativeElementsFinder(resolver)
                visitor.visitTerminalRule(rule)
                return visitor.ruleElementsList
            }
        }

        override fun visitTerminalAlternatives(o: XtextTerminalAlternatives) {
            o.terminalGroupList.forEach {
                visitTerminalGroup(it)
                if (it != o.terminalGroupList.last()) {
                    ruleElementsList.add(TerminalKeywordElement(XtextElementFactory.createKeyword("\"|\"")))
                }
//                getPipePsiElementIfExists(it)?.let {
//                    ruleElementsList.add(TerminalSimpleElement(it))
//                }
            }
        }

        override fun visitTerminalToken(o: XtextTerminalToken) {
            super.visitTerminalToken(o)
            o.plusKeyword?.let {
                ruleElementsList.add(TerminalSimpleElement(it))
            }
            o.quesMarkKeyword?.let {
                ruleElementsList.add(TerminalSimpleElement(it))
            }
            o.asteriskKeyword?.let {
                ruleElementsList.add(TerminalSimpleElement(it))
            }
        }

        override fun visitCharacterRange(o: XtextCharacterRange) {
            ruleElementsList.add(TerminalRangeElement(o))
        }

        override fun visitParenthesizedTerminalElement(o: XtextParenthesizedTerminalElement) {
            ruleElementsList.add(TerminalSimpleElement(o.lBracketKeyword))
            visitTerminalAlternatives(o.terminalAlternatives)
            ruleElementsList.add(TerminalSimpleElement(o.rBracketKeyword))

        }

        override fun visitWildcard(o: XtextWildcard) {
            ruleElementsList.add(TerminalSimpleElement(o))

        }

        override fun visitAbstractNegatedToken(o: XtextAbstractNegatedToken) {
            o.negatedToken?.let {
                visitNegatedToken(it)
            }
            o.untilToken?.let {
                ruleElementsList.add(TerminalUntilElement(it))
            }
        }

        override fun visitTerminalRuleCall(o: XtextTerminalRuleCall) {
            ruleElementsList.add(TerminalRuleCallElelment(o, resolver))
        }

        override fun visitNegatedToken(o: XtextNegatedToken) {
            ruleElementsList.add(TerminalNegatedElement(o))
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
}