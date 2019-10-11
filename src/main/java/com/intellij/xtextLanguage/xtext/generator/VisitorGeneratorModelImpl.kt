package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.*

class VisitorGeneratorModelImpl(override val xtextRules: MutableList<XtextParserRule>, val terminalRules: List<String>) : VisitorGeneratorModel {
    override var rules = culcRulesForNameVisitor(xtextRules)
    override fun getUniqueName(rule: VisitorGeneratorModel.modelRule): String? {
        return rule.UniqueName
    }

    override fun getXtextRuleName(rule: VisitorGeneratorModel.modelRule): String {
        return rule.name
    }

    override fun hasUniqueId(rule: VisitorGeneratorModel.modelRule): Boolean {
        return rule.UniqueId
    }

    override fun hasId(rule: VisitorGeneratorModel.modelRule): Boolean {
        return rule.Id
    }

    override fun getRulesRuleCalls(rule: VisitorGeneratorModel.modelRule): List<String> {
        return rule.RuleCalls
    }

    override fun getRulesRuleCallsList(rule: VisitorGeneratorModel.modelRule): List<String> {
        return rule.RuleCallsList

    }

    fun culcRulesForNameVisitor(xtextRules: MutableList<XtextParserRule>): MutableList<VisitorGeneratorModel.modelRule> {
        val newList = ArrayList<VisitorGeneratorModel.modelRule>()
        xtextRules.forEach {
            val newModelRule = VisitorGeneratorModel.modelRule(it.ruleNameAndParams.validID.text)
            culcRuleRecursively(it.alternatives, newModelRule, true)
            if (newModelRule.RuleCalls.size > 0) {
                newModelRule.RuleCalls = newModelRule.RuleCalls.distinct() as MutableList<String>

            }
            newList.add(newModelRule)
        }

        return newList
    }

    fun culcRuleRecursively(xtextAlternatives: XtextAlternatives, modelRule: VisitorGeneratorModel.modelRule, firstCall: Boolean) {
        xtextAlternatives.conditionalBranchList.forEach {
            if (it.unorderedGroup == null) return@forEach
            val listOfRuleCalls = ArrayList<String>()
            it.getBranchTokens().forEach {
                if (it.asterisk == null && it.quesMark == null && it.plus == null) {
                    it.abstractTerminal?.let {
                        it.ruleCall?.let {
                            computeRuleCall(firstCall, xtextAlternatives.conditionalBranchList.size, it.referenceAbstractRuleRuleID.text, modelRule, listOfRuleCalls)

                        }
                        it.predicatedRuleCall?.let {
                            computeRuleCall(firstCall, xtextAlternatives.conditionalBranchList.size, it.referenceAbstractRuleRuleID.text, modelRule, listOfRuleCalls)

                        }
                        it.parenthesizedElement?.let { culcRuleRecursively(it.alternatives, modelRule, false) }
                        it.predicatedGroup?.let { culcRuleRecursively(it.alternatives, modelRule, false) }
                    }
                    it.assignment?.let {
                        if (it.validID.text == "name" && xtextAlternatives.conditionalBranchList.size == 1 && it.assignableTerminal.ruleCall != null && firstCall) {
                            it.assignableTerminal.ruleCall?.let {
                                modelRule.UniqueName = createGrammarKitKeywordName(it.referenceAbstractRuleRuleID.text)
                            }
                        }
                        it.assignableTerminal.ruleCall?.let {
                            computeRuleCall(firstCall, xtextAlternatives.conditionalBranchList.size, it.referenceAbstractRuleRuleID.text, modelRule, listOfRuleCalls)

                        }
                        it.assignableTerminal.parenthesizedAssignableElement?.let {
                            computeAssignableAlternatives(it.assignableAlternatives, modelRule)
                        }
                    }
                }
            }
            val map = HashMap<String, Int>()
            listOfRuleCalls.forEach {
                val ruleCall = it
                map.get(ruleCall)?.let {
                    map.set(ruleCall, it + 1)
                } ?: map.put(ruleCall, 1)


            }
            map.filter { it.value > 1 }.forEach {
                modelRule.RuleCallsList.add(it.key)
            }
            modelRule.RuleCallsList.forEach {
                val name = it
                listOfRuleCalls.filter { it == name }.forEach { listOfRuleCalls.remove(it) }
            }
            modelRule.RuleCalls.addAll(listOfRuleCalls)

        }
    }

    fun createGrammarKitKeywordName(name: String): String {
        if (name == "ID") return "Id"

        return name
    }

    fun computeRuleCall(firstCall: Boolean, numberOfBranches: Int, name: String, modelRule: VisitorGeneratorModel.modelRule, list: ArrayList<String>) {
        if (firstCall && numberOfBranches == 1 && name == "ID") {
            modelRule.UniqueId = true
            return
        } else if (name == "ID") {
            modelRule.Id = true
        } else {
            if (!terminalRules.contains(name)) {
                list.add(name)
            }

        }
    }

    fun computeAssignableAlternatives(assignableAlternatives: XtextAssignableAlternatives, modelRule: VisitorGeneratorModel.modelRule) {
        assignableAlternatives.assignableTerminalList.forEach {
            it.ruleCall?.let {
                modelRule.RuleCalls.add(it.referenceAbstractRuleRuleID.text)
            }
            it.parenthesizedAssignableElement?.let {
                computeAssignableAlternatives(it.assignableAlternatives, modelRule)
            }
        }

    }

    fun computeRuleCall(ruleCall: XtextREFERENCEAbstractRuleRuleID, modelRule: VisitorGeneratorModel.modelRule) {
        modelRule.RuleCalls.add(ruleCall.text)

    }

    companion object {
        fun XtextConditionalBranch.getBranchTokens(): List<XtextAbstractTokenWithCardinality> {
            return this.unorderedGroup?.groupList
                    ?.flatMap { it.abstractTokenList }
                    ?.mapNotNull { it.abstractTokenWithCardinality }
                    ?: emptyList()
        }

    }
}