package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*

class TerminalRule : ModelRule {
    override lateinit var name: String
    var isFragment = false
    override lateinit var returnTypeText: String
    override val alternativeElements = mutableListOf<RuleElement>()
    override var isDataTypeRule = true

    constructor(myRule: XtextTerminalRule) {
        name = myRule.validID.text.replace("^", "")
        returnTypeText = myRule.typeRef?.text ?: "String"
        alternativeElements.addAll(AlternativeElementsFinder.getRuleElementListOfTerminalRule(myRule))
        isFragment = myRule.fragmentKeyword != null
    }

    constructor()


    class AlternativeElementsFinder() : XtextVisitor() {
        val ruleElementsList = mutableListOf<TerminalRuleElement>()


        companion object {
            fun getRuleElementListOfTerminalRule(rule: XtextTerminalRule): MutableList<TerminalRuleElement> {

                val visitor = AlternativeElementsFinder()
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
            ruleElementsList.add(TerminalRuleCallElelment(o))
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