package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class VisitorGeneratorModelImpl(xtextRules: MutableList<XtextParserRule>, val terminalRules: List<String>) : VisitorGeneratorModel {
    override var rules = culcRulesForNameVisitor(xtextRules)


    fun culcRulesForNameVisitor(xtextRules: MutableList<XtextParserRule>): MutableList<VisitorGeneratorModel.ModelRule> {
        val listOfModelRules = mutableListOf<VisitorGeneratorModel.ModelRule>()
        xtextRules.forEach {
            val rule = it

            val visitorUniqueName = XtextVisitorUniqueName()
            visitorUniqueName.visitParserRule(it)

            val visitorAllRuleCalls = XtextVisitorAllRuleCalls()
            visitorAllRuleCalls.visitParserRule(it)


            val ruleCalls = computeRuleCallList(visitorAllRuleCalls.RuleCalls).toMutableList()
            val listRepeating = mutableListOf<String>()
            ruleCalls.forEach {
                val visitor3 = XtextVisitorRepeatingRuleCalls(it)
                visitor3.visitParserRule(rule)
                if (visitor3.isRepeating) listRepeating.add(it)
            }


            val modelRule = VisitorGeneratorModel.ModelRule()
            var ruleName = createGkitRuleName(it.ruleNameAndParams.validID.text)

            listRepeating.forEach {
                ruleCalls.remove(it)
            }

            modelRule.name = ruleName.capitalize()
            modelRule.ruleCalls = ruleCalls
            modelRule.ruleCallsList = listRepeating
            modelRule.uniqueName = visitorUniqueName.uniqueName
            listOfModelRules.add(modelRule)
        }
        return listOfModelRules
    }


    fun computeRuleCallList(list: MutableList<String>): List<String> {
        val newList = list.distinct().toMutableList()
        return removeTerminalRuleCallsFromList(newList).map {
            createGkitRuleName(it)
        }
    }

    fun removeTerminalRuleCallsFromList(list: MutableList<String>): List<String> {

        return list.filter { !terminalRules.contains(it) || it == "ID" }
    }


    fun createGkitRuleName(oldName: String): String {
        var newName: String
        if (oldName.startsWith("^")) {
            newName = oldName.replace("^", "caret").capitalize()
        } else {
            newName = oldName.capitalize()
        }
        return newName
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


