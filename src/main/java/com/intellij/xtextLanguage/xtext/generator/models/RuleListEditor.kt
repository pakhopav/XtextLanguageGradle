package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.psi.XtextElementFactory
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class RuleListEditor(private val ruleList: MutableList<XtextParserRule>) {

    fun changeLeavesInRule(ruleName: String, leavesToChange: Map<LeafPsiElement, String>) {
        val newRule = createNewRuleFromAnotherByChangingLeaves(getRuleByName(ruleName)!!, leavesToChange)
        deleteRule(ruleName)
        ruleList.add(newRule)
    }

    fun editRuleByName(ruleName: String, ruleBody: String) {
        deleteRule(ruleName)
        addRule(ruleName, ruleBody)
    }

    fun deleteRule(ruleName: String) {
        val ruleToRemove = ruleList.first { it.ruleNameAndParams.validID.text == ruleName }
        ruleList.remove(ruleToRemove)
    }

    fun addRule(ruleName: String, ruleBody: String) {
        val ruleText = "$ruleName : $ruleBody"
        ruleList.add(XtextElementFactory.createParserRule(ruleText))
    }

    fun addRuleIfAbsent(ruleName: String, ruleBody: String) {
        if (getRuleByName(ruleName) == null) {
            val ruleText = "$ruleName : $ruleBody;"
            ruleList.add(XtextElementFactory.createParserRule(ruleText))
        }

    }

    fun duplicateRuleWithNewName(oldName: String, newName: String) {
        val body = getRuleByName(oldName)?.alternatives?.text
        body?.let { addRuleIfAbsent(newName, it) }
    }


    fun getRuleByName(ruleName: String): XtextParserRule? {
        return ruleList.firstOrNull { it.ruleNameAndParams.validID.text == ruleName }
    }


    fun createNewRuleFromAnotherByChangingLeaves(oldRule: XtextParserRule, leavesToChange: Map<LeafPsiElement, String>): XtextParserRule {
        var nextLeaf: PsiElement? = PsiTreeUtil.firstChild(oldRule.alternatives)
        val leavesToChangeIterator = leavesToChange.keys.iterator()
        var nextLeafToChange = leavesToChangeIterator.next()
        val newRuleBodyStringBuilder = StringBuilder()
        while (nextLeaf != null) {
            if (nextLeaf == nextLeafToChange) {
                newRuleBodyStringBuilder.append(leavesToChange[nextLeafToChange] + " ")
                if (leavesToChangeIterator.hasNext()) nextLeafToChange = leavesToChangeIterator.next()
            } else {
                newRuleBodyStringBuilder.append(nextLeaf.text + " ")
            }
            nextLeaf = PsiTreeUtil.nextLeaf(nextLeaf)

        }
        val ruleText = "${oldRule.ruleNameAndParams.validID.text} : $newRuleBodyStringBuilder"
        return XtextElementFactory.createParserRule(ruleText)
    }


}