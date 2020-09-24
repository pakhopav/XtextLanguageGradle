package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*
import java.util.*

open class ParserRule : ModelRule {
    override lateinit var name: String
    override lateinit var returnTypeText: String
    var bnfExtensionsStrings = mutableListOf<String>()
    override val alternativeElements = mutableListOf<RuleElement>()

    override var isDataTypeRule = false
    var isReferenced = false
    var isReferencedDelegateRule = false
    var isPrivate = false

    private val visitor = XtextParserRuleVisitor()

    //    constructor(myRule: XtextParserRule) {
//        name = myRule.ruleNameAndParams.validID.text.replace("^", "Caret").capitalize()
//        returnTypeText = myRule.typeRef?.text ?: name
//        alternativeElements.addAll(AlternativeElementsFinder.getAlternativeElementsListOfParserRule(myRule))
//
//    }
    constructor(myRule: XtextParserRule) {
        name = myRule.ruleNameAndParams.validID.text.replace("^", "").capitalize()
        alternativeElements.addAll(visitor.getAlternativeElementsListOfParserRule(myRule))
        returnTypeText = (visitor.getChangedType() ?: myRule.typeRef?.text ?: "").replace("^", "")
        isPrivate = myRule.fragmentKeyword != null
    }

    constructor()
//    constructor(name: String, returnType : String, alternativeElements: MutableList<out RuleElement>){
//        this.name = name
//        this.returnType = returnType
//        this.alternativeElements = alternativeElements
//    }

//    fun addStringToBnfExtension(string: String) {
//        bnfExtensionsStrings += string + "\n"
//    }


//    class XtextParserRuleVisitor : XtextVisitor() {
//        private var listOfAlternativesElements = mutableListOf<RuleElement>()
//        private var lastAction: String? = null
//        private var changedReturnType = ""
//        private var isActionSibling = false
//        private var currentRuleName = ""
//
//        fun getAlternativeElementsListOfParserRule(rule: XtextParserRule): List<RuleElement> {
//            clearAll()
//            currentRuleName = rule.ruleNameAndParams.validID.text.replace("^", "").capitalize()
//
//            visitAlternatives(rule.alternatives)
//            return listOfAlternativesElements
//        }
//
//        fun getChangedType(): String? {
//            return if (changedReturnType.isEmpty()) null else changedReturnType
//        }
//
//        private fun clearAll() {
//            listOfAlternativesElements = mutableListOf()
//            lastAction = null
//            changedReturnType = ""
//        }
//
//
//        private fun addElementToList(element: RuleElement, assignment: String) {
//            lastAction?.let {
//                if (element !is BnfServiceElement) {
//                    element.action = lastAction as String
//                }
//            }
//            element.assignment = assignment
//            listOfAlternativesElements.add(element)
//        }
//
//
//        private fun isOptionalToken(token: XtextAbstractTokenWithCardinality): Boolean {
//            return token.quesMarkKeyword != null || token.asteriskKeyword != null
//        }
//
//
//        override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
//            val actionCopy = lastAction
////            val actionSibling = isActionSibling
////            if (actionSibling) isActionSibling = false
//            val inOptional = isOptionalToken(o)
//            o.abstractTerminal?.let {
//                visitAbstractTerminal(it)
//            }
//            o.assignment?.let {
//                visitAssignment(it)
//            }
//            o.quesMarkKeyword?.let {
//                addElementToList(BnfServiceElement(it), "")
//            }
//            o.plusKeyword?.let {
//                addElementToList(BnfServiceElement(it), "")
//            }
//            o.asteriskKeyword?.let {
//                addElementToList(BnfServiceElement(it), "")
//            }
////            if (actionSibling) isActionSibling = true
//            if(inOptional && lastAction == null && actionCopy != null){
//                lastAction = actionCopy
//            }
//            if (!inOptional) {
//                lastAction = null
////                isActionSibling = false
//            }
//
//        }
//
//        override fun visitAssignment(o: XtextAssignment) {
//            var assignmentString = o.validID.text.replace("^", "")
//            o.equalsKeyword?.let { assignmentString = "$assignmentString=" }
//            o.plusEqualsKeyword?.let { assignmentString = "$assignmentString+=" }
//            o.quesEqualsKeyword?.let { assignmentString = "$assignmentString?=" }
//
//            visitAssignableTerminal(o.assignableTerminal, assignmentString)
//        }
//
////        override fun visitCrossReference(o: XtextCrossReference) {
////            addElementToList(ParserCrossReferenceElement(o), "")
////        }
//
//        override fun visitAlternatives(alternatives: XtextAlternatives) {
////            var markFirstElementsOfEveryBranchWithAction = false
////            if (lastAction != null && !inOptionalToken && alternatives.conditionalBranchList.size > 1) markFirstElementsOfEveryBranchWithAction = true
//            alternatives.conditionalBranchList.forEach {
////                if (markFirstElementsOfEveryBranchWithAction) lastAction = simpleAction
//                visitConditionalBranch(it)
//                getPipePsiElementIfExists(it)?.let {
//                    addElementToList(BnfServiceElement(it), "")
//                }
//            }
//
//        }
//
//        override fun visitConditionalBranch(o: XtextConditionalBranch) {
//            super.visitConditionalBranch(o)
//            if (isActionSibling) {
//                isActionSibling = false
//                lastAction = null
//            }
//        }
//
//        fun visitAssignableAlternatives(o: XtextAssignableAlternatives, assignmentString: String) {
//            o.assignableTerminalList.forEach {
//                visitAssignableTerminal(it, assignmentString)
//                if (it != o.assignableTerminalList.last()) {
//                }
//                getPipePsiElementIfExists(it)?.let {
//                    addElementToList(BnfServiceElement(it), "")
//                }
//            }
//        }
//
//        fun visitAssignableTerminal(o: XtextAssignableTerminal, assignmentString: String) {
//            o.keyword?.let {
//                addElementToList(ParserSimpleElement(it.string), assignmentString)
//            }
//            o.ruleCall?.let {
//                addElementToList(ParserRuleCallElement(it.referenceAbstractRuleRuleID), assignmentString)
//            }
//            o.parenthesizedAssignableElement?.let {
//                visitParenthesizedAssignableElement(it, assignmentString)
//            }
//            o.crossReference?.let {
//                addElementToList(ParserCrossReferenceElement(it, currentRuleName), assignmentString)
//            }
//        }
//
//        fun getPipePsiElementIfExists(o: PsiElement): PsiElement? {
//            o.nextSibling?.let {
//                if (it.node.elementType == XtextTypes.PIPE_KEYWORD) {
//                    return it
//                } else {
//                    it.nextSibling?.let {
//                        if (it.node.elementType == XtextTypes.PIPE_KEYWORD) {
//                            return it
//                        }
//                    }
//                }
//
//
//            }
//            return null
//        }
//
//        override fun visitUnorderedGroup(o: XtextUnorderedGroup) {
//            if (o.groupList.size == 1) {
//                visitGroup(o.groupList.first())
//            } else {
//                throw Exception("Plugin does not support unordered groups")
//            }
//        }
//
//        override fun visitKeyword(o: XtextKeyword) {
//            addElementToList(ParserSimpleElement(o.string), "")
//        }
//
//
//        fun visitParenthesizedAssignableElement(o: XtextParenthesizedAssignableElement, assignmentString: String) {
//            addElementToList(BnfServiceElement(o.lBracketKeyword), "")
//            visitAssignableAlternatives(o.assignableAlternatives, assignmentString)
//            addElementToList(BnfServiceElement(o.rBracketKeyword), "")
//
//        }
//
//        override fun visitParenthesizedElement(o: XtextParenthesizedElement) {
//            addElementToList(BnfServiceElement(o.lBracketKeyword), "")
//            visitAlternatives(o.alternatives)
//            addElementToList(BnfServiceElement(o.rBracketKeyword), "")
//
//        }
//
//
//        override fun visitPredicatedGroup(o: XtextPredicatedGroup) {
//            addElementToList(BnfServiceElement(o.lBracketKeyword), "")
//
//            visitAlternatives(o.alternatives)
//            addElementToList(BnfServiceElement(o.rBracketKeyword), "")
//
//        }
//
//        override fun visitPredicatedKeyword(o: XtextPredicatedKeyword) {
//            addElementToList(ParserSimpleElement(o.string), "")
//
//        }
//
//
//        override fun visitRuleID(o: XtextRuleID) {
//            addElementToList(ParserSimpleElement(o), "")
//        }
//
//
//        override fun visitValidID(o: XtextValidID) {
//            o.id?.let {
//                addElementToList(ParserSimpleElement(it), "")
//            }
//        }
//
//        override fun visitRuleCall(o: XtextRuleCall) {
//            addElementToList(ParserRuleCallElement(o.referenceAbstractRuleRuleID), "")
//        }
//
//        override fun visitPredicatedRuleCall(o: XtextPredicatedRuleCall) {
//            addElementToList(ParserRuleCallElement(o.referenceAbstractRuleRuleID), "")
//        }
//
//        override fun visitAction(o: XtextAction) {
//            if (o.validID == null && !inBrackets(o)) {
//                changedReturnType = o.typeRef.text
//            } else {
//                lastAction = o.text
//                isActionSibling = true
//            }
//        }
//
//
//        private fun inBrackets(element: PsiElement): Boolean {
//            val b1 = PsiTreeUtil.getParentOfType(element, XtextParenthesizedElement::class.java) != null
//            val b2 = PsiTreeUtil.getParentOfType(element, XtextPredicatedGroup::class.java) != null
//            val b3 = b1 || b2
//            print("")
//            return b3
//        }
//    }

    class XtextParserRuleVisitor : XtextVisitor() {
        private var listOfAlternativesElements = mutableListOf<RuleElement>()
        private var lastAction: String? = null
        private var changedReturnType = ""

        //        private var isActionSibling = false
        private var currentRuleName = ""

        /*
        stack of actual info about last action visited if the action isn`t followed by not optional token.
        All elements to be added to result list marked
         */
        val visitedActionsStack = Stack<ActionInfo>()
        private var l = 0

        fun getAlternativeElementsListOfParserRule(rule: XtextParserRule): List<RuleElement> {
            clearAll()
            currentRuleName = rule.ruleNameAndParams.validID.text.replace("^", "").capitalize()

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
                    lastAction = null
                }
            }
            element.assignment = assignment
            if (visitedActionsStack.isNotEmpty()) element.suffixMarker = visitedActionsStack.peek()
            listOfAlternativesElements.add(element)
        }


        private fun isOptionalToken(token: XtextAbstractTokenWithCardinality): Boolean {
            return token.quesMarkKeyword != null || token.asteriskKeyword != null
        }

        private fun hasCardinality(token: XtextAbstractTokenWithCardinality): Boolean {
            return token.quesMarkKeyword != null || token.asteriskKeyword != null || token.plusKeyword != null
        }

        private fun goodElement(token: XtextAbstractTokenWithCardinality): Boolean {
            if (hasCardinality(token)) return false
            token.abstractTerminal?.parenthesizedElement?.let { parenthesizedElement ->
                parenthesizedElement.alternatives.conditionalBranchList.forEach { branch ->
                    val firstTokenInBrackets = PsiTreeUtil.getChildrenOfType(branch, XtextAbstractTokenWithCardinality::class.java)?.get(0)
                    firstTokenInBrackets?.let {
                        if (!goodElement(it)) return false
                    } ?: return false
                }
            }
            return true
        }

        override fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality) {
            if (lastAction != null) {
                if (goodElement(o) && !visitedActionsStack.empty() && visitedActionsStack.peek().text == lastAction) {
                    visitedActionsStack.pop()
                } else {
                    lastAction = null
                }
//                if(hasCoordinality(o)){
//                    lastAction = null
//                }else if(!visitedActionsStack.empty() && visitedActionsStack.peek().text == lastAction){
//                    visitedActionsStack.pop()
//                }
            }


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

        }

        override fun visitAbstractTerminal(o: XtextAbstractTerminal) {
            o.keyword?.let {
                visitKeyword(it)
            }
            o.ruleCall?.let {
                visitRuleCall(it)
            }
            o.parenthesizedElement?.let {
                visitParenthesizedElement(it)
            }
            o.predicatedKeyword?.let {
                visitPredicatedKeyword(it)
            }
            o.predicatedRuleCall?.let {
                visitPredicatedRuleCall(it)
            }
            o.predicatedGroup?.let {
                visitPredicatedGroup(it)
            }
        }

        override fun visitAssignment(o: XtextAssignment) {
            var assignmentString = o.validID.text.replace("^", "")
            o.equalsKeyword?.let { assignmentString = "$assignmentString=" }
            o.plusEqualsKeyword?.let { assignmentString = "$assignmentString+=" }
            o.quesEqualsKeyword?.let { assignmentString = "$assignmentString?=" }

            visitAssignableTerminal(o.assignableTerminal, assignmentString)
        }

//        override fun visitCrossReference(o: XtextCrossReference) {
//            addElementToList(ParserCrossReferenceElement(o), "")
//        }

        override fun visitAlternatives(alternatives: XtextAlternatives) {
            val lastActionOnEntry = lastAction
            alternatives.conditionalBranchList.forEach {
                if (lastActionOnEntry != null && lastAction == null) lastAction = lastActionOnEntry
                visitConditionalBranch(it)
                getPipePsiElementIfExists(it)?.let {
                    addElementToList(BnfServiceElement(it), "")
                }
            }

        }

        override fun visitConditionalBranch(o: XtextConditionalBranch) {
            super.visitConditionalBranch(o)
        }

        fun visitAssignableAlternatives(o: XtextAssignableAlternatives, assignmentString: String) {
            val lastActionOnEntry = lastAction
            o.assignableTerminalList.forEach {
                if (lastActionOnEntry != null && lastAction == null) lastAction = lastActionOnEntry
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
                addElementToList(ParserCrossReferenceElement(it, currentRuleName), assignmentString)
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
            l++
            visitAssignableAlternatives(o.assignableAlternatives, assignmentString)
            l--
            removeIrrelevantActionsFromStack()
            addElementToList(BnfServiceElement(o.rBracketKeyword), "")

        }

        override fun visitParenthesizedElement(o: XtextParenthesizedElement) {
            addElementToList(BnfServiceElement(o.lBracketKeyword), "")
            l++
            visitAlternatives(o.alternatives)
            l--
            removeIrrelevantActionsFromStack()
            addElementToList(BnfServiceElement(o.rBracketKeyword), "")

        }

        private fun removeIrrelevantActionsFromStack() {
            while (visitedActionsStack.isNotEmpty()) {
                if (visitedActionsStack.peek().l > l) {
                    visitedActionsStack.pop()
                } else {
                    break
                }
            }
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
                visitedActionsStack.push(ActionInfo(o.text, l))
            }
        }


        private fun inBrackets(element: PsiElement): Boolean {
            val b1 = PsiTreeUtil.getParentOfType(element, XtextParenthesizedElement::class.java) != null
            val b2 = PsiTreeUtil.getParentOfType(element, XtextPredicatedGroup::class.java) != null
            return b1 || b2
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
//    private fun filterLiteralAttributes(list: List<XtextAssignableTerminal>): List<XtextAssignableTerminal> {
//        val res = mutableListOf<XtextAssignableTerminal>()
//
//        return res
//    }
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
        copy.bnfExtensionsStrings = bnfExtensionsStrings
        copy.isPrivate = isPrivate
        copy.isReferenced = isReferenced
        copy.isReferencedDelegateRule = isReferencedDelegateRule
        return copy
    }

    class ActionInfo(val text: String, val l: Int)

}