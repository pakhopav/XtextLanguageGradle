package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.*
import java.util.*

class ParserRuleCreator : ModelRuleCreator {
    private val visitor = XtextParserRuleVisitor()


    override fun createRule(psiRule: PsiElement): ParserRuleCreationOutput? {
        if (psiRule is XtextParserRule) {
            return createRuleFromXtextParserRule(psiRule)
        } else {
            return null
        }
    }


    private fun createRuleFromXtextParserRule(rule: XtextParserRule): ParserRuleCreationOutput {
        val resultRule = ParserRule()
        resultRule.name = rule.ruleNameAndParams.validID.text.replace("^", "").capitalize()
        visitor.visitRule(rule)
        resultRule.alternativeElements.addAll(visitor.getAlternativeElementsList())
        resultRule.returnTypeText = visitor.getReturnType()
        resultRule.isPrivate = rule.fragmentKeyword != null

        return ParserRuleCreationOutput(resultRule, visitor.getSuffixList())
    }


    class ParserRuleCreationOutput(rule: ParserRule, suffixList: List<ParserRule>) : ModelRuleCreator.CreationOutput {
        private val rule = rule
        private val suffixList = suffixList

        override fun getRule() = rule
        override fun getSuffixList() = suffixList
    }


    class XtextParserRuleVisitor : XtextVisitor() {
        private var listOfAlternativesElements = mutableListOf<RuleElement>()
        private var lastAction: String? = null
        private var changedReturnType: String? = null
        private var markedElements = mutableMapOf<RuleElement, ActionInfo>()
        private var suffixList = mutableListOf<ParserRule>()
        private var currentRuleName = ""
        private var ruleReturnType = ""

        /*
        stack of actual info about last action visited if the action isn`t followed by not optional token.
        All elements to be added to result list marked
         */
        val visitedActionsStack = Stack<ActionInfo>()
        private var l = 0

        fun visitRule(rule: XtextParserRule) {
            clearAll()
            currentRuleName = rule.ruleNameAndParams.validID.text.replace("^", "").capitalize()
            visitAlternatives(rule.alternatives)
            ruleReturnType = (changedReturnType ?: rule.typeRef?.text ?: "").replace("^", "")
            refactorSuffixes()
        }

        fun getAlternativeElementsList() = listOfAlternativesElements

        fun getSuffixList() = suffixList

        fun getReturnType() = ruleReturnType

        private fun clearAll() {
            listOfAlternativesElements = mutableListOf()
            markedElements = mutableMapOf()
            suffixList = mutableListOf()
            lastAction = null
            changedReturnType = ""
            currentRuleName = ""
            ruleReturnType = ""
        }


        private fun refactorSuffixes() {
            var currentMarkedElementsGroup: List<RuleElement>
            var suffixCounter = 0
            while (markedElements.isNotEmpty()) {
                val highestLevelActionText = markedElements.maxBy { it.value.l }?.value?.text
                highestLevelActionText?.let {
                    currentMarkedElementsGroup = markedElements.filter { it.value.text == highestLevelActionText }.map { it.key }
                    currentMarkedElementsGroup.forEach { markedElements.remove(it) }

                    val newRuleName = "${currentRuleName}Suffix${if (suffixCounter == 0) "" else suffixCounter++}"
                    val newRuleCallElement = ParserRuleCallElement(XtextElementFactory.createValidID(newRuleName))
                    newRuleCallElement.action = highestLevelActionText
                    val index = listOfAlternativesElements.indexOf(currentMarkedElementsGroup[0])
                    listOfAlternativesElements.add(index, newRuleCallElement)
                    listOfAlternativesElements.removeAll(currentMarkedElementsGroup)

                    val newParserRule = ParserRule()
                    newParserRule.name = newRuleName
                    newParserRule.returnTypeText = ruleReturnType
                    newParserRule.alternativeElements.addAll(currentMarkedElementsGroup)
                    newParserRule.bnfExtensionsStrings.add("implements = \"com.intellij.xtextLanguage.xtext.psi.SuffixElement\"")
                    suffixList.add(newParserRule)
                }
                suffixCounter++
            }
        }


        private fun addElementToList(element: RuleElement, assignment: String) {
            lastAction?.let {
                if (element !is BnfServiceElement) {
                    element.action = lastAction as String
                    lastAction = null
                }
            }
            element.assignment = assignment
            if (visitedActionsStack.isNotEmpty()) {
                markedElements.put(element, visitedActionsStack.peek())
            }
            listOfAlternativesElements.add(element)
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

    class ActionInfo(val text: String, val l: Int)

}