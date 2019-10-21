package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*

class ParserRule(val myRule: XtextParserRule) {
    var name = myRule.ruleNameAndParams.validID.text.replace("^", "Caret").capitalize()
    var isFragment: Boolean = myRule.fragmentKeyword != null
    var returnType: String = myRule.typeRef?.text ?: name
    val attributes: Map<String, XtextAssignableTerminal> = AttributesFinder.getAttributeMapOfParserRule(myRule)
    val alternativesElements: List<RuleElement> = AlternativeElementsFinder.getAlternativeElementsListOfParserRule(myRule)
    var isReferenced = false


    class AlternativeElementsFinder : XtextVisitor() {
        val listOfAlternativesElements = mutableListOf<RuleElement>()

        companion object {
            fun getAlternativeElementsListOfParserRule(rule: XtextParserRule): List<RuleElement> {

                val visitor = AlternativeElementsFinder()
                visitor.visitAlternatives(rule.alternatives)
                return visitor.listOfAlternativesElements
            }
        }


        override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
            o.abstractTerminal?.let {
                visitAbstractTerminal(it)
            }
            o.assignment?.let {
                visitAssignment(it)
            }
            o.quesMarkKeyword?.let {
                listOfAlternativesElements.add(ParserSimpleElement(it))
            }
            o.plusKeyword?.let {
                listOfAlternativesElements.add(ParserSimpleElement(it))
            }
            o.asteriskKeyword?.let {
                listOfAlternativesElements.add(ParserSimpleElement(it))
            }
        }

        override fun visitCrossReference(o: XtextCrossReference) {
            listOfAlternativesElements.add(ParserCrossReferenseElement(o))
        }

        override fun visitAlternatives(o: XtextAlternatives) {
            o.conditionalBranchList.forEach {
                visitConditionalBranch(it)
                if (it != o.conditionalBranchList.last()) {
                }
                getPipePsiElementIfExists(it)?.let {
                    listOfAlternativesElements.add(ParserSimpleElement(it))
                }
            }
        }


        override fun visitAssignableAlternatives(o: XtextAssignableAlternatives) {
            o.assignableTerminalList.forEach {
                visitAssignableTerminal(it)
                if (it != o.assignableTerminalList.last()) {
                }
                getPipePsiElementIfExists(it)?.let {
                    listOfAlternativesElements.add(ParserSimpleElement(it))
                }
            }
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


        override fun visitKeyword(o: XtextKeyword) {
            listOfAlternativesElements.add(ParserSimpleElement(o.string))
        }


        override fun visitParenthesizedAssignableElement(o: XtextParenthesizedAssignableElement) {
            listOfAlternativesElements.add(ParserSimpleElement(o.lBracketKeyword))
            visitAssignableAlternatives(o.assignableAlternatives)
            listOfAlternativesElements.add(ParserSimpleElement(o.rBracketKeyword))

        }

        override fun visitParenthesizedElement(o: XtextParenthesizedElement) {
            listOfAlternativesElements.add(ParserSimpleElement(o.lBracketKeyword))

            visitAlternatives(o.alternatives)
            listOfAlternativesElements.add(ParserSimpleElement(o.rBracketKeyword))

        }


        override fun visitPredicatedGroup(o: XtextPredicatedGroup) {
            listOfAlternativesElements.add(ParserSimpleElement(o.lBracketKeyword))

            visitAlternatives(o.alternatives)
            listOfAlternativesElements.add(ParserSimpleElement(o.rBracketKeyword))

        }

        override fun visitPredicatedKeyword(o: XtextPredicatedKeyword) {
            listOfAlternativesElements.add(ParserSimpleElement(o.string))

        }


        override fun visitRuleID(o: XtextRuleID) {
            listOfAlternativesElements.add(ParserSimpleElement(o))
        }


        override fun visitValidID(o: XtextValidID) {
            o.id?.let {
                listOfAlternativesElements.add(ParserSimpleElement(it))

            }
        }

        override fun visitRuleCall(o: XtextRuleCall) {
            listOfAlternativesElements.add(ParserRuleCallElement(o.referenceAbstractRuleRuleID))
        }

        override fun visitPredicatedRuleCall(o: XtextPredicatedRuleCall) {
            listOfAlternativesElements.add(ParserRuleCallElement(o.referenceAbstractRuleRuleID))
        }


    }


    class AttributesFinder : XtextVisitor() {
        val attributes: MutableMap<String, XtextAssignableTerminal> = HashMap()

        companion object {
            fun getAttributeMapOfParserRule(rule: XtextParserRule): Map<String, XtextAssignableTerminal> {

                val visitor = AttributesFinder()
                visitor.visitParserRule(rule)
                return visitor.attributes
            }
        }

        override fun visitAssignment(o: XtextAssignment) {
            attributes.put(o.validID.text, o.assignableTerminal)
        }


    }


}