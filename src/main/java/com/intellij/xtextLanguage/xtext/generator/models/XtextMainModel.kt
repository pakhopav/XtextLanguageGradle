package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.generator.RuleResolverImpl
import com.intellij.xtextLanguage.xtext.generator.models.elements.EnumRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.TerminalRule
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitorActions
import com.intellij.xtextLanguage.xtext.psi.*

class XtextMainModel(xtextFiles: List<XtextFile>) {
    val parserRules: List<ParserRule>
    val enumRules: List<EnumRule>
    val terminalRules: List<TerminalRule>
    val refactoredXtextParserRules: List<XtextParserRule>
    val referencesModel: XtextCrossReferencesModel
    val keywordModel: XtextKeywordModel
    lateinit var visitorGeneratorModel: VisitorGeneratorModel
    val ruleResolver = RuleResolverImpl(this)


    init {
        val mutableListOfParserRules = mutableListOf<ParserRule>()
        val mutableListOfEnumRules = mutableListOf<EnumRule>()
        val mutableListOfTerminalRules = mutableListOf<TerminalRule>()
        val mutableListOfXtextAbstractRules = mutableListOf<XtextAbstractRule>()
        val mutableListOfRefactoredXtextParserRules = mutableListOf<XtextParserRule>()

        val refactorer = TreeRefactor()

        xtextFiles.forEach {
            val xtextParserRules = PsiTreeUtil.findChildrenOfType(it, XtextParserRule::class.java).toList()
            mutableListOfXtextAbstractRules.addAll(PsiTreeUtil.findChildrenOfType(it, XtextAbstractRule::class.java).toList())
            val refactoredXtextParserRules = refactorer.getRefactoredXtextRules(xtextParserRules)
            mutableListOfRefactoredXtextParserRules.addAll(refactoredXtextParserRules)
            mutableListOfParserRules.addAll(refactoredXtextParserRules
                    .map { ParserRule(it) })
            mutableListOfEnumRules.addAll(PsiTreeUtil.findChildrenOfType(it, XtextEnumRule::class.java)
                    .map { EnumRule(it) })
            mutableListOfTerminalRules.addAll(PsiTreeUtil.findChildrenOfType(it, XtextTerminalRule::class.java)
                    .map { TerminalRule(it, ruleResolver) })
        }

        parserRules = mutableListOfParserRules
        enumRules = mutableListOfEnumRules
        terminalRules = mutableListOfTerminalRules
        refactoredXtextParserRules = mutableListOfRefactoredXtextParserRules

        keywordModel = XtextKeywordModel(mutableListOfXtextAbstractRules)
        referencesModel = XtextCrossReferencesModel(parserRules)
        setReferencedFieldForParcerRules()
        createVisitorGeneratorModel()

    }

    fun setReferencedFieldForParcerRules() {
        referencesModel.references.forEach {
            val target = it.refetenceTarget.text
            parserRules.forEach {
                if (it.returnType == target) {
                    it.isReferenced = true
                }
            }
        }

    }


    fun createVisitorGeneratorModel() {
        val terminalRulesNames = terminalRules.map { it.name }
        visitorGeneratorModel = VisitorGeneratorModelImpl(refactoredXtextParserRules, terminalRulesNames)

    }


    class TreeRefactor {

        fun getRefactoredXtextRules(oldRules: List<XtextParserRule>): List<XtextParserRule> {
            val refactoredRules = mutableListOf<XtextParserRule>()
            oldRules.forEach {
                var rule = it
                val branchesToChange = XtextVisitorActions.getBranchesWithActionsInParserRule(it)
                branchesToChange.forEach {
                    val newRule = getNewRuleFromBrunch(rule, it.key, it.value)
                    refactoredRules.add(newRule)
                    rule = getReconstructedRule(rule, it.key, newRule.ruleNameAndParams.text)
                }
                refactoredRules.add(rule)

            }
            return refactoredRules

        }

        fun getNewRuleFromBrunch(mainRule: XtextParserRule, branch: XtextConditionalBranch, action: XtextAction): XtextParserRule {
            val branchNumber = getBranchNumber(branch)
            val ruleName = mainRule.ruleNameAndParams.text.replace("^", "Caret").capitalize()
            val newRuleName = "RuleFrom${ruleName}Branch$branchNumber"
            val newRule = XtextElementFactory.createParserRule("$newRuleName returns ${action.typeRef.text} : ${branch.text};")

            return newRule
        }

        fun getBranchNumber(branch: XtextConditionalBranch): Int {
            val parent = branch.parent as XtextAlternatives
            var branchNumber = 0
            var i = 1
            parent.conditionalBranchList.forEach {
                if (it == branch) branchNumber = i
                i++
            }
            return branchNumber
        }

        fun getReconstructedRule(mainRule: XtextParserRule, branch: XtextConditionalBranch, nameOfNewRule: String): XtextParserRule {
            val sb = StringBuilder()
            var firstLeaf = PsiTreeUtil.getDeepestVisibleFirst(mainRule)
            while (firstLeaf?.node?.elementType != XtextTypes.COLON_KEYWORD) {
                sb.append(firstLeaf?.text)
                firstLeaf?.let { firstLeaf = PsiTreeUtil.nextLeaf(it) }

            }
            sb.append(": ")
            val branches = mainRule.alternatives.conditionalBranchList
            branches.let {
                it.forEach {
                    if (it == branch) {
                        sb.append(nameOfNewRule)
                    } else {
                        sb.append(it.text)
                    }
                    if (it != branches.last()) sb.append("| ")

                }
            }
            sb.append(";")

            return XtextElementFactory.createParserRule(sb.toString())
        }
    }
}