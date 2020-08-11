package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*

open class ParserRule(val myRule: XtextParserRule) {
    var name = myRule.ruleNameAndParams.validID.text.replace("^", "Caret").capitalize()
    var returnType: String = myRule.typeRef?.text ?: name
    var bnfExtentionsString = ""
    var alternativesElements: MutableList<RuleElement> = AlternativeElementsFinder.getAlternativeElementsListOfParserRule(myRule).toMutableList()
    var isReferenced = false
    var isDataTypeRule = false
    var isPrivate = false

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

        override fun visitAssignment(o: XtextAssignment) {
            var assignmentString = o.validID.text
            o.equalsKeyword?.let { assignmentString = "$assignmentString=" }
            o.plusEqualsKeyword?.let { assignmentString = "$assignmentString+=" }
            o.quesEqualsKeyword?.let { assignmentString = "$assignmentString?=" }

            visitAssignableTerminal(o.assignableTerminal, assignmentString)
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


        fun visitAssignableAlternatives(o: XtextAssignableAlternatives, assignmentString: String) {
            o.assignableTerminalList.forEach {
                visitAssignableTerminal(it, assignmentString)
                if (it != o.assignableTerminalList.last()) {
                }
                getPipePsiElementIfExists(it)?.let {
                    listOfAlternativesElements.add(ParserSimpleElement(it))
                }
            }
        }

        fun visitAssignableTerminal(o: XtextAssignableTerminal, assignmentString: String) {
            o.keyword?.let {
                val element = ParserSimpleElement(it.string)
                element.assignment = assignmentString
                listOfAlternativesElements.add(element)
            }
            o.ruleCall?.let {
                val element = ParserRuleCallElement(it.referenceAbstractRuleRuleID)
                element.assignment = assignmentString
                listOfAlternativesElements.add(element)
            }
            o.parenthesizedAssignableElement?.let {
                visitParenthesizedAssignableElement(it, assignmentString)
            }
            o.crossReference?.let {
                val element = ParserCrossReferenseElement(it)
                element.assignment = assignmentString
                listOfAlternativesElements.add(element)
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

        override fun visitUnorderedGroup(o: XtextUnorderedGroup) {
            if (o.groupList.size == 1) {
                visitGroup(o.groupList.first())
            } else {
                throw Exception("Plugin does not support unordered groups")
            }
        }

        override fun visitKeyword(o: XtextKeyword) {
            listOfAlternativesElements.add(ParserSimpleElement(o.string))
        }


        fun visitParenthesizedAssignableElement(o: XtextParenthesizedAssignableElement, assignmentString: String) {
            listOfAlternativesElements.add(ParserSimpleElement(o.lBracketKeyword))
            visitAssignableAlternatives(o.assignableAlternatives, assignmentString)
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


    class AssignedActionsFinder : XtextVisitor() {
        val actions: MutableMap<XtextAction, PsiElement> = HashMap()

        companion object {
            fun findAssignedActions(rule: XtextParserRule): Map<XtextAction, PsiElement> {

                val visitor = AssignedActionsFinder()
                visitor.visitParserRule(rule)
                return visitor.actions
            }
        }

        override fun visitAction(o: XtextAction) {
            o.validID?.let {
                val nextPsiElement = PsiTreeUtil.getNextSiblingOfType(o, XtextAbstractTokenWithCardinality::class.java)
                actions.put(o, nextPsiElement as PsiElement)
            }
        }


    }


    private fun filterLiteralAttributes(list: List<XtextAssignableTerminal>): List<XtextAssignableTerminal> {
        val res = mutableListOf<XtextAssignableTerminal>()

        return res
    }
//    private fun isLiteralAttribute(attribute: XtextAssignableTerminal): Boolean{
//        attribute.keyword?.let { return false }
//        attribute.crossReference?.let { return false }
//        attribute.ruleCall?.let {
//            if()
//            isDataTypeRule(it) }
//        return false
//    }


    fun copy(): ParserRule {
        val copy = ParserRule(myRule)
        for (i in alternativesElements.indices) {
            if (alternativesElements[i].refactoredName != null) copy.alternativesElements[i].refactoredName = alternativesElements[i].refactoredName
        }
        copy.isDataTypeRule = isDataTypeRule
        copy.isReferenced = isReferenced
        return copy
    }


}