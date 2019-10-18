package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.*

class XtextNameVisitor {
    var uniqueName: String? = null
    var ruleCalls = mutableListOf<String>()
    val ruleCallLists = mutableListOf<String>()
    var isNameAvailable = false

    fun visitParserRule(o: XtextParserRule) {
        visitAlternatives(o.alternatives)
    }

    fun visitAbstractTerminal(o: XtextAbstractTerminal, ruleCallsInBranch: MutableList<String>) {
        val abstractTokenWithCardinality = o.parent as XtextAbstractTokenWithCardinality
        if (abstractTokenWithCardinality.quesMark != null || abstractTokenWithCardinality.asterisk != null || abstractTokenWithCardinality.plus != null) {
            o.ruleCall?.let {
                if (ruleCallsInBranch.contains(it.referenceAbstractRuleRuleID.text)) {
                    ruleCallsInBranch.add(it.referenceAbstractRuleRuleID.text)
                }
            }
            o.predicatedRuleCall?.let {
                if (ruleCallsInBranch.contains(it.referenceAbstractRuleRuleID.text)) {
                    ruleCallsInBranch.add(it.referenceAbstractRuleRuleID.text)
                }
            }
            o.predicatedGroup?.let {
                visitAlternativesOptional(it.alternatives, ruleCallsInBranch)
            }
            o.parenthesizedElement?.let {
                visitAlternativesOptional(it.alternatives, ruleCallsInBranch)
            }
        } else {
            o.ruleCall?.let {
                ruleCallsInBranch.add(it.referenceAbstractRuleRuleID.text)
            }
            o.predicatedRuleCall?.let {
                ruleCallsInBranch.add(it.referenceAbstractRuleRuleID.text)
            }
            o.predicatedGroup?.let {
                val newVisitor = XtextNameVisitor()
                newVisitor.visitAlternatives(it.alternatives)
                ruleCallsInBranch.addAll(newVisitor.ruleCalls)
                ruleCallLists.addAll(newVisitor.ruleCallLists)
            }
            o.parenthesizedElement?.let {
                val newVisitor = XtextNameVisitor()
                newVisitor.visitAlternatives(it.alternatives)
                ruleCallsInBranch.addAll(newVisitor.ruleCalls)
                ruleCallLists.addAll(newVisitor.ruleCallLists)
            }
        }

    }

    fun visitAlternativesOptional(o: XtextAlternatives, ruleCallsInBranch: MutableList<String>) {
        o.conditionalBranchList.forEach {
            visitConditionalBranchOptional(it, ruleCallsInBranch)
        }
    }

    fun visitConditionalBranchOptional(o: XtextConditionalBranch, ruleCallsInBranch: MutableList<String>) {
        o.unorderedGroup?.let {
            visitUnorderedGroupOptional(it, ruleCallsInBranch)
        }
    }

    fun visitUnorderedGroupOptional(o: XtextUnorderedGroup, ruleCallsInBranch: MutableList<String>) {
        o.groupList.forEach {
            visitGroupOptional(it, ruleCallsInBranch)
        }
    }

    fun visitGroupOptional(o: XtextGroup, ruleCallsInBranch: MutableList<String>) {
        o.abstractTokenList.forEach {
            visitAbstractTokenOptional(it, ruleCallsInBranch)
        }
    }

    fun visitAbstractTokenOptional(o: XtextAbstractToken, ruleCallsInBranch: MutableList<String>) {
        o.abstractTokenWithCardinality?.let {
            visitAbstractTokenWithCardinalityOptional(it, ruleCallsInBranch)
        }
    }

    fun visitAbstractTokenWithCardinalityOptional(o: XtextAbstractTokenWithCardinality, ruleCallsInBranch: MutableList<String>) {
        o.abstractTerminal?.let {
            visitAbstractTerminalOptional(it, ruleCallsInBranch)
        }
        o.assignment?.let {
            visitAssignmentOptional(it, ruleCallsInBranch)
        }
    }

    fun visitAbstractTerminalOptional(o: XtextAbstractTerminal, ruleCallsInBranch: MutableList<String>) {
        o.ruleCall?.let {
            if (ruleCallsInBranch.contains(it.referenceAbstractRuleRuleID.text)) {
                ruleCallsInBranch.add(it.referenceAbstractRuleRuleID.text)
            }
        }
        o.predicatedRuleCall?.let {
            if (ruleCallsInBranch.contains(it.referenceAbstractRuleRuleID.text)) {
                ruleCallsInBranch.add(it.referenceAbstractRuleRuleID.text)
            }
        }
        o.predicatedGroup?.let {
            visitAlternativesOptional(it.alternatives, ruleCallsInBranch)
        }
        o.parenthesizedElement?.let {
            visitAlternativesOptional(it.alternatives, ruleCallsInBranch)
        }
    }

    fun visitAssignmentOptional(o: XtextAssignment, ruleCallsInBranch: MutableList<String>) {
        visitAssignableTerminalOptional(o.assignableTerminal, ruleCallsInBranch)
    }

    fun visitAssignableTerminalOptional(o: XtextAssignableTerminal, ruleCallsInBranch: MutableList<String>) {
        o.ruleCall?.let {
            if (ruleCallsInBranch.contains(it.referenceAbstractRuleRuleID.text)) {
                ruleCallsInBranch.add(it.referenceAbstractRuleRuleID.text)
            }
        }
        o.parenthesizedAssignableElement?.let {
            visitAssignableAlternativesOptional(it.assignableAlternatives, ruleCallsInBranch)
        }

    }

    fun visitAssignableAlternativesOptional(o: XtextAssignableAlternatives, ruleCallsInBranch: MutableList<String>) {
        o.assignableTerminalList.forEach {
            visitAssignableTerminalOptional(it, ruleCallsInBranch)
        }
    }

    fun visitAbstractToken(o: XtextAbstractToken, ruleCallsInBranch: MutableList<String>) {
        o.abstractTokenWithCardinality?.let {
            visitAbstractTokenWithCardinality(it, ruleCallsInBranch)
        }
    }

    fun visitAbstractTokenWithCardinality(o: XtextAbstractTokenWithCardinality, ruleCallsInBranch: MutableList<String>) {
        o.abstractTerminal?.let { visitAbstractTerminal(it, ruleCallsInBranch) }
        o.assignment?.let { visitAssignment(it, ruleCallsInBranch) }
    }


    fun visitAlternatives(o: XtextAlternatives) {
        val parent = o.parent
        isNameAvailable = parent is XtextParserRule && o.conditionalBranchList.size == 1
        o.conditionalBranchList.forEach {
            visitConditionalBranch(it)
        }
        ruleCalls = ruleCalls.distinct().toMutableList()

    }


    fun visitAssignableAlternatives(o: XtextAssignableAlternatives, ruleCallsInBranch: MutableList<String>) {
        o.assignableTerminalList.forEach {
            visitAssignableTerminal(it, ruleCallsInBranch)
        }
    }

    fun visitAssignableTerminal(o: XtextAssignableTerminal, ruleCallsInBranch: MutableList<String>) {
        o.ruleCall?.let {
            if ((o.parent as XtextAssignment).validID.text == "name" && isNameAvailable) {
                uniqueName = it.referenceAbstractRuleRuleID.text
                if (!ruleCallsInBranch.contains(it.referenceAbstractRuleRuleID.text)) {
                    ruleCallsInBranch.add(it.referenceAbstractRuleRuleID.text)
                }
                isNameAvailable = false
            } else {
                if (!ruleCallsInBranch.contains(it.referenceAbstractRuleRuleID.text)) {
                    ruleCallsInBranch.add(it.referenceAbstractRuleRuleID.text)
                }
            }
        }
        o.parenthesizedAssignableElement?.let {
            visitAssignableAlternatives(it.assignableAlternatives, ruleCallsInBranch)
        }

    }

    fun visitAssignment(o: XtextAssignment, ruleCallsInBranch: MutableList<String>) {
        visitAssignableTerminal(o.assignableTerminal, ruleCallsInBranch)
    }


    fun visitConditionalBranch(o: XtextConditionalBranch) {
        var ruleCallsInBranch = mutableListOf<String>()

        o.unorderedGroup?.let {
            visitUnorderedGroup(it, ruleCallsInBranch)
        }

        val map = HashMap<String, Int>()
        ruleCallsInBranch.forEach {
            val ruleCall = it
            map.get(ruleCall)?.let {
                map.set(ruleCall, it + 1)
            } ?: map.put(ruleCall, 1)
        }
        ruleCallsInBranch = ruleCallsInBranch.distinct().toMutableList()
        map.filter { it.value > 1 }.forEach {
            ruleCallLists.add(it.key)
            ruleCallsInBranch.remove(it.key)
        }


        ruleCalls.addAll(ruleCallsInBranch)


    }


    fun visitGroup(o: XtextGroup, ruleCallsInBranch: MutableList<String>) {
        o.abstractTokenList.forEach {
            visitAbstractToken(it, ruleCallsInBranch)
        }
    }


    fun visitUnorderedGroup(o: XtextUnorderedGroup, ruleCallsInBranch: MutableList<String>) {
        o.groupList.forEach {
            visitGroup(it, ruleCallsInBranch)
        }
    }


}