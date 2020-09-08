package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*

open class ParserRule : ModelRule {
    override lateinit var name: String
    override lateinit var returnTypeText: String
    var bnfExtensionsString = ""
        private set
    override val alternativeElements = mutableListOf<RuleElement>()
    var isReferenced = false
    override var isDataTypeRule = false
    var isPrivate = false
    var isApiRule = false

    private val visitor = XtextParserRuleVisitor()

    //    constructor(myRule: XtextParserRule) {
//        name = myRule.ruleNameAndParams.validID.text.replace("^", "Caret").capitalize()
//        returnTypeText = myRule.typeRef?.text ?: name
//        alternativeElements.addAll(AlternativeElementsFinder.getAlternativeElementsListOfParserRule(myRule))
//
//    }
    constructor(myRule: XtextParserRule) {
        name = myRule.ruleNameAndParams.validID.text.replace("^", "Caret").capitalize()
        alternativeElements.addAll(visitor.getAlternativeElementsListOfParserRule(myRule))
        returnTypeText = visitor.getChangedType() ?: myRule.typeRef?.text ?: ""
    }

    constructor()
//    constructor(name: String, returnType : String, alternativeElements: MutableList<out RuleElement>){
//        this.name = name
//        this.returnType = returnType
//        this.alternativeElements = alternativeElements
//    }

    fun addStringToBnfExtension(string: String) {
        bnfExtensionsString += string + "\n"
    }

    class AlternativeElementsFinder : XtextVisitor() {
        var listOfAlternativesElements = mutableListOf<RuleElement>()
        private var lastAction: String? = null
        private var simpleAction = ""
        private var inOptionalToken = false
        var changedReturnType = ""


        fun getAlternativeElementsListOfParserRule(rule: XtextParserRule): List<RuleElement> {
            clearAll()
            visitAlternatives(rule.alternatives)
            return listOfAlternativesElements
        }

        private fun clearAll() {
            listOfAlternativesElements = mutableListOf()
        }

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
            addElementToList(ParserCrossReferenceElement(o), "")
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
                addElementToList(ParserCrossReferenceElement(it), assignmentString)
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


    class XtextParserRuleVisitor : XtextVisitor() {
        private var listOfAlternativesElements = mutableListOf<RuleElement>()
        private var lastAction: String? = null
        private var changedReturnType = ""
        private var isActionSibling = false


        fun getAlternativeElementsListOfParserRule(rule: XtextParserRule): List<RuleElement> {
            clearAll()
            visitAlternatives(rule.alternatives)
            return listOfAlternativesElements
        }

        fun getChangedType(): String? {
            return if (changedReturnType.isEmpty()) null else changedReturnType
        }

        private fun clearAll() {
            listOfAlternativesElements = mutableListOf()
            lastAction = null
            changedReturnType = ""
        }


        private fun addElementToList(element: RuleElement, assignment: String) {
            lastAction?.let {
                if (element !is BnfServiceElement) {
                    element.action = lastAction as String
                }
            }
            element.assignment = assignment
            listOfAlternativesElements.add(element)
        }


        private fun isOptionalToken(token: XtextAbstractTokenWithCardinality): Boolean {
            return token.quesMarkKeyword != null || token.asteriskKeyword != null
        }


        override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
            val actionSibling = isActionSibling
            if (actionSibling) isActionSibling = false
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
            if (actionSibling) isActionSibling = true
            if (!isOptionalToken(o) && isActionSibling) lastAction = null

        }

        override fun visitAssignment(o: XtextAssignment) {
            var assignmentString = o.validID.text
            o.equalsKeyword?.let { assignmentString = "$assignmentString=" }
            o.plusEqualsKeyword?.let { assignmentString = "$assignmentString+=" }
            o.quesEqualsKeyword?.let { assignmentString = "$assignmentString?=" }

            visitAssignableTerminal(o.assignableTerminal, assignmentString)
        }

        override fun visitCrossReference(o: XtextCrossReference) {
            addElementToList(ParserCrossReferenceElement(o), "")
        }

        override fun visitAlternatives(alternatives: XtextAlternatives) {
//            var markFirstElementsOfEveryBranchWithAction = false
//            if (lastAction != null && !inOptionalToken && alternatives.conditionalBranchList.size > 1) markFirstElementsOfEveryBranchWithAction = true
            alternatives.conditionalBranchList.forEach {
//                if (markFirstElementsOfEveryBranchWithAction) lastAction = simpleAction
                visitConditionalBranch(it)
                getPipePsiElementIfExists(it)?.let {
                    addElementToList(BnfServiceElement(it), "")
                }
            }

        }

        override fun visitConditionalBranch(o: XtextConditionalBranch) {
            super.visitConditionalBranch(o)
            if (isActionSibling) {
                isActionSibling = false
                lastAction = null
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
                addElementToList(ParserCrossReferenceElement(it), assignmentString)
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
            if (o.validID == null && !inBrackets(o)) {
                changedReturnType = o.typeRef.text
            } else {
                lastAction = o.text
                isActionSibling = true
            }
        }


        private fun inBrackets(element: PsiElement): Boolean {
            val b1 = PsiTreeUtil.getParentOfType(element, XtextParenthesizedElement::class.java) != null
            val b2 = PsiTreeUtil.getParentOfType(element, XtextPredicatedGroup::class.java) != null
            val b3 = b1 || b2
            print("")
            return b3
        }
    }


//    class AssignedActionsFinder : XtextVisitor() {
//        val actions: MutableMap<XtextAction, PsiElement> = HashMap()
//
//        companion object {
//            fun findAssignedActions(rule: XtextParserRule): Map<XtextAction, PsiElement> {
//
//                val visitor = AssignedActionsFinder()
//                visitor.visitParserRule(rule)
//                return visitor.actions
//            }
//        }
//
//        override fun visitAction(o: XtextAction) {
//            o.validID?.let {
//                val nextPsiElement = PsiTreeUtil.getNextSiblingOfType(o, XtextAbstractTokenWithCardinality::class.java)
//                actions.put(o, nextPsiElement as PsiElement)
//            }
//        }
//
//
//    }


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
        val copy = ParserRule()
        copy.name = name
        copy.returnTypeText = returnTypeText
        copy.alternativeElements.addAll(alternativeElements)
        copy.isDataTypeRule = isDataTypeRule
        copy.bnfExtensionsString = bnfExtensionsString
        copy.isPrivate = isPrivate
        copy.isApiRule = isApiRule
        copy.isReferenced = isReferenced
        return copy
    }


}