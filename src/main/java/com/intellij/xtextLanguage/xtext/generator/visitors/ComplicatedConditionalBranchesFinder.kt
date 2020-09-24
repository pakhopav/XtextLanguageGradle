package com.intellij.xtextLanguage.xtext.generator.visitors

import com.intellij.xtextLanguage.xtext.psi.XtextAlternatives
import com.intellij.xtextLanguage.xtext.psi.XtextConditionalBranch
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule

class ComplicatedConditionalBranchesFinder : XtextVisitor() {

    private val complicatedBranches = mutableListOf<XtextConditionalBranch>()


    companion object {
        fun getComplicateBranchesForParserRule(rule: XtextParserRule): List<XtextConditionalBranch> {

            val visitor = ComplicatedConditionalBranchesFinder()
            visitor.visitParserRule(rule)
            return visitor.complicatedBranches
        }
    }

    override fun visitAlternatives(o: XtextAlternatives) {
        if (o.conditionalBranchList.size > 1) {
            o.conditionalBranchList.forEach {
                if (!isSimpleBranch(it)) complicatedBranches.add(it)
            }
        }
    }


    fun isSimpleBranch(conditionalBranch: XtextConditionalBranch): Boolean {
        val branchAbstractTokens = conditionalBranch.unorderedGroup?.groupList
                ?.flatMap { it.abstractTokenList }
                ?.map { it.abstractTokenWithCardinality }
        branchAbstractTokens?.let { tokens ->
            if (tokens.size == 1) {
                tokens[0]!!.abstractTerminal?.let {
                    it.ruleCall?.let { return true }
                    it.keyword?.let { return true }
                }
            }
        }
        return false


//        var tempElement: PsiElement = conditionalBranch
//        for (k: Int in 0..5) {
//            if (tempElement is LeafPsiElement) return false
//            tempElement = tempElement.firstChild
//        }
//        return tempElement is XtextRuleCall


//        conditionalBranch.firstChild?.let {
//            if(it is XtextGroup) it.firstChild?.let {
//                if(it is XtextAbstractToken) it.firstChild?.let {
//                    if(it is XtextAbstractTokenWithCardinality) it.firstChild.let {
//                        if(it is XtextAbstractTerminal) it.firstChild?.let {
//                            if(it is )
//                        }
//                    }
//                }
//            }
//        }
    }
}