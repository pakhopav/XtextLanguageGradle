package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*

open class ParserRule(val myRule: XtextParserRule) : ModelRule() {
    override var name = myRule.ruleNameAndParams.validID.text.replace("^", "Caret").capitalize()
    override var returnType: String = findMyReturnType()
    var bnfExtensionsString = ""
    override var alternativeElements: MutableList<out RuleElement> = AlternativeElementsFinder.getAlternativeElementsListOfParserRule(myRule).toMutableList()
    var isReferenced = false
    override var isDataTypeRule = false
    var isPrivate = false
    var isApiRule = false

    class AlternativeElementsFinder : XtextVisitor() {
        var listOfAlternativesElements = mutableListOf<RuleElement>()
        private var lastAction: String? = null
        private var simpleAction = ""
        private var inOptionalToken = false


        companion object {
            fun getAlternativeElementsListOfParserRule(rule: XtextParserRule): List<RuleElement> {

                val visitor = AlternativeElementsFinder()
                visitor.visitAlternatives(rule.alternatives)
                return visitor.listOfAlternativesElements
            }
        }

        private fun addElementToList(element: RuleElement, assignment: String) {
            lastAction?.let {
                if (element !is BnfServiceElement && !inOptionalToken) {
                    element.action = lastAction as String
                    lastAction = null
                }
            }
            element.assignment = assignment
            listOfAlternativesElements.add(element)
        }


        private fun isOptionalToken(token: XtextAbstractTokenWithCardinality): Boolean {
            return token.quesMarkKeyword != null || token.asteriskKeyword != null
        }




        override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
            if (isOptionalToken(o) && lastAction != null) inOptionalToken = true
            o.abstractTerminal?.let {
                visitAbstractTerminal(it)
            }
            o.assignment?.let {
                visitAssignment(it)
            }
            o.quesMarkKeyword?.let {
                addElementToList(BnfServiceElement(it), "")
            }
            o.plusKeyword?.let {
                addElementToList(BnfServiceElement(it), "")
            }
            o.asteriskKeyword?.let {
                addElementToList(BnfServiceElement(it), "")
            }
            inOptionalToken = false

        }

        override fun visitAssignment(o: XtextAssignment) {
            var assignmentString = o.validID.text
            o.equalsKeyword?.let { assignmentString = "$assignmentString=" }
            o.plusEqualsKeyword?.let { assignmentString = "$assignmentString+=" }
            o.quesEqualsKeyword?.let { assignmentString = "$assignmentString?=" }

            visitAssignableTerminal(o.assignableTerminal, assignmentString)
        }

        override fun visitCrossReference(o: XtextCrossReference) {
            addElementToList(ParserCrossReferenseElement(o), "")
        }

        override fun visitAlternatives(alternatives: XtextAlternatives) {
            var markFirstElementsOfEveryBranchWithAction = false
            if (lastAction != null && !inOptionalToken && alternatives.conditionalBranchList.size > 1) markFirstElementsOfEveryBranchWithAction = true
            alternatives.conditionalBranchList.forEach {
                if (markFirstElementsOfEveryBranchWithAction) lastAction = simpleAction
                visitConditionalBranch(it)
                if (it != alternatives.conditionalBranchList.last()) {
                }
                getPipePsiElementIfExists(it)?.let {
                    addElementToList(BnfServiceElement(it), "")
                }
            }
        }


        fun visitAssignableAlternatives(o: XtextAssignableAlternatives, assignmentString: String) {
            o.assignableTerminalList.forEach {
                visitAssignableTerminal(it, assignmentString)
                if (it != o.assignableTerminalList.last()) {
                }
                getPipePsiElementIfExists(it)?.let {
                    addElementToList(BnfServiceElement(it), "")
                }
            }
        }

        fun visitAssignableTerminal(o: XtextAssignableTerminal, assignmentString: String) {
            o.keyword?.let {
                addElementToList(ParserSimpleElement(it.string), assignmentString)
            }
            o.ruleCall?.let {
                addElementToList(ParserRuleCallElement(it.referenceAbstractRuleRuleID), assignmentString)
            }
            o.parenthesizedAssignableElement?.let {
                visitParenthesizedAssignableElement(it, assignmentString)
            }
            o.crossReference?.let {
                addElementToList(ParserCrossReferenseElement(it), assignmentString)
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
            addElementToList(ParserSimpleElement(o.string), "")
        }


        fun visitParenthesizedAssignableElement(o: XtextParenthesizedAssignableElement, assignmentString: String) {
            addElementToList(BnfServiceElement(o.lBracketKeyword), "")
            visitAssignableAlternatives(o.assignableAlternatives, assignmentString)
            addElementToList(BnfServiceElement(o.rBracketKeyword), "")

        }

        override fun visitParenthesizedElement(o: XtextParenthesizedElement) {
            addElementToList(BnfServiceElement(o.lBracketKeyword), "")
            visitAlternatives(o.alternatives)
            addElementToList(BnfServiceElement(o.rBracketKeyword), "")

        }


        override fun visitPredicatedGroup(o: XtextPredicatedGroup) {
            addElementToList(BnfServiceElement(o.lBracketKeyword), "")

            visitAlternatives(o.alternatives)
            addElementToList(BnfServiceElement(o.rBracketKeyword), "")

        }

        override fun visitPredicatedKeyword(o: XtextPredicatedKeyword) {
            addElementToList(ParserSimpleElement(o.string), "")

        }


        override fun visitRuleID(o: XtextRuleID) {
            addElementToList(ParserSimpleElement(o), "")
        }


        override fun visitValidID(o: XtextValidID) {
            o.id?.let {
                addElementToList(ParserSimpleElement(it), "")
            }
        }

        override fun visitRuleCall(o: XtextRuleCall) {
            addElementToList(ParserRuleCallElement(o.referenceAbstractRuleRuleID), "")
        }

        override fun visitPredicatedRuleCall(o: XtextPredicatedRuleCall) {
            addElementToList(ParserRuleCallElement(o.referenceAbstractRuleRuleID), "")
        }

        override fun visitAction(o: XtextAction) {
            lastAction = o.text
            simpleAction = lastAction!!
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
private fun findMyReturnType(): String {
    return myRule.typeRef?.referenceEcoreEClassifier?.text ?: name
}

    fun copy(): ParserRule {
        val copy = ParserRule(myRule)
        for (i in alternativeElements.indices) {
            if (alternativeElements[i].refactoredName != null) copy.alternativeElements[i].refactoredName = alternativeElements[i].refactoredName
        }
        copy.isDataTypeRule = isDataTypeRule
        copy.isReferenced = isReferenced
        return copy
    }


}