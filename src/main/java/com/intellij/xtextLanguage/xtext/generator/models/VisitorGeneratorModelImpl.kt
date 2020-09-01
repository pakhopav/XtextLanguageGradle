package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.visitors.AllRuleCallsFinder
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitorRepeatingRuleCalls
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitorUniqueName
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class VisitorGeneratorModelImpl(xtextRules: List<XtextParserRule>, private val terminalRulesNames: List<String>, val rulesWithSuperClass: Map<String, String>, crossReferenceNames: List<String>) : VisitorGeneratorModel {
    val ruleCallsFinder = AllRuleCallsFinder(crossReferenceNames)
    override var rules = culcRulesForNameVisitor(xtextRules)

    fun culcRulesForNameVisitor(xtextRules: List<XtextParserRule>): MutableList<VisitorGeneratorModel.ModelRule> {
        val listOfModelRules = mutableListOf<VisitorGeneratorModel.ModelRule>()
        xtextRules.forEach { rule ->
            val allRuleCalls = ruleCallsFinder.getAllRuleCallsInParserRule(rule)
            val ruleCalls = computeRuleCallList(allRuleCalls).toMutableList()
            val listRepeating = mutableListOf<String>()
            val repeatedRulesWithSuperclass = mutableListOf<String>()
            ruleCalls.forEach {
                if (rulesWithSuperClass.keys.contains(it) && !repeatedRulesWithSuperclass.contains(it)) {
                    val superClass = rulesWithSuperClass[it]
                    val allSubclasses = rulesWithSuperClass.keys.filter { rulesWithSuperClass[it] == superClass }.toList().distinct()
                    if (XtextVisitorRepeatingRuleCalls.doesRuleCallRepeatsInBranchOfParserRule(rule, allSubclasses)) {
                        listRepeating.add(superClass!!)
                        repeatedRulesWithSuperclass.addAll(allSubclasses)

                    }
                } else if (XtextVisitorRepeatingRuleCalls.doesRuleCallRepeatsInBranchOfParserRule(rule, listOf(it))) {
                    listRepeating.add(it)
                }
            }
            repeatedRulesWithSuperclass.forEach {
                ruleCalls.remove(it)
            }
            listRepeating.forEach {
                ruleCalls.remove(it)
            }

            val modelRule = VisitorGeneratorModel.ModelRule()
            modelRule.name = rule.ruleNameAndParams.validID.text.replace("^", "Caret").capitalize()
            modelRule.uniqueName = XtextVisitorUniqueName.getUniqueNameOfParserRule(rule)
            modelRule.ruleCalls = ruleCalls
            modelRule.ruleCallsList = listRepeating
            listOfModelRules.add(modelRule)
        }
        return listOfModelRules
    }


    fun computeRuleCallList(list: MutableList<String>): List<String> {
        val newList = list.distinct().toMutableList()
        return removeTerminalRuleCallsFromList(newList).map {
            it.replace("^", "Caret").capitalize()
        }
    }

    fun removeTerminalRuleCallsFromList(list: MutableList<String>): List<String> {
        return list.filter { !terminalRulesNames.contains(it) || it == "ID" }
    }




//    fun culcRulesForNameVisitor(xtextRules: MutableList<XtextParserRule>): MutableList<VisitorGeneratorModel.ModelRule>{
//        val listOfModelRules = mutableListOf<VisitorGeneratorModel.ModelRule>()
//        xtextRules.forEach {
//            val visitor = XtextNameVisitor()
//            visitor.visitParserRule(it)
//            val modelRule =  VisitorGeneratorModel.ModelRule()
//            var ruleName =it.ruleNameAndParams.validID.text
//            if (ruleName.startsWith("^")){
//                ruleName  = ruleName.replace("^", "caret" )
//            }
//            modelRule.name = ruleName.capitalize()
//            modelRule.ruleCalls= computeRuleCallList(visitor.ruleCalls)
//            modelRule.ruleCallsList = computeRuleCallList(visitor.ruleCallLists)
//            modelRule.uniqueName = visitor.uniqueName
//            listOfModelRules.addAll(visitor.newModelRules)
//            listOfModelRules.add(modelRule)
//        }
//        return  listOfModelRules
//    }
}


