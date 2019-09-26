package com.intellij.xtextLanguage.xtext.generator

import com.intellij.psi.PsiComment
import com.intellij.psi.PsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.psi.*

class BnfGeneratorUtil(val fileModel: XtextFileModel) {
    fun getEnumRuleDeclarationsAsString(rule: XtextEnumRule): String {
        val sb = java.lang.StringBuilder()
        val leteralDclarations = rule.enumLiterals?.enumLiteralDeclarationList
        if (leteralDclarations != null) {
            for (declaration in leteralDclarations) {
                if (declaration.keyword != null) {
                    sb.append(declaration.keyword?.text)
                } else {
                    sb.append(declaration.referenceEcoreEEnumLiteral.id.text)
                }
                sb.append("${if (declaration != leteralDclarations.last()) {
                    "| "
                } else {
                    ""
                }} ")
            }
        }
        return sb.toString()
    }


    fun getRuleAlternativesAsString(rule: XtextParserRule?): String {
        val sb = StringBuilder()
        if (rule == null) return ""
        val branches = rule.alternatives?.conditionalBranchList
        if (branches == null) return ""
        for (b in branches) {
            writebrunch(b, sb)
            sb.append("${if (b != branches.last()) {
                "| "
            } else {
                ""
            }} ")
        }
        return sb.toString()
    }

    fun writebrunch(b: XtextConditionalBranch, sb: StringBuilder): String {
        val unorderedGroup = b.getUnorderedGroup() ?: return ""
        val groups = unorderedGroup.getGroupList()
        val tokens = ArrayList<XtextAbstractToken>()
        groups.forEach { tokens.addAll(it.abstractTokenList) }
        val abstractTokens = ArrayList<XtextAbstractTokenWithCardinality>()
        tokens.forEach { abstractTokens.add(it.abstractTokenWithCardinality ?: return@forEach) }
        if (abstractTokens.size == 0) return ""
//        abstractTokens.forEach {if (it.abstractTerminal != null)writeAbstractTerminal(it.abstractTerminal!!, sb)
//                                else if(it.assignment != null)writeAssignableTerminal(it.assignment!!.assignableTerminal, sb)
//        }
        abstractTokens.forEach {
            it.abstractTerminal?.let { writeAbstractTerminal(it, sb) }
            it.assignment?.assignableTerminal?.let { writeAssignableTerminal(it, sb) }
            it.asterisk?.let { sb.append("${it.text} ") }
            it.plus?.let { sb.append("${it.text} ") }
            it.quesMark?.let { sb.append("${it.text} ") }
        }
        return sb.toString()
    }


    private fun writeAbstractTerminal(abstractTerminal: XtextAbstractTerminal, sb: StringBuilder): String {
        val keyword = abstractTerminal.keyword
        val ruleCall = abstractTerminal.ruleCall
        val parenthesizedElement = abstractTerminal.parenthesizedElement
        val pKeyword = abstractTerminal.predicatedKeyword
        val pRuleCall = abstractTerminal.predicatedRuleCall
        val pParenthesizedElement = abstractTerminal.predicatedGroup
        keyword?.let { sb.append("${it.text} ") }
        ruleCall?.let { sb.append("${it.referenceAbstractRuleRuleID.text} ") }
        parenthesizedElement?.let {
            val branches = it.alternatives.conditionalBranchList
            sb.append("(")
            branches.forEach {
                writebrunch(it, sb)
                sb.append("${if (it != branches.last()) {
                    "| "
                } else {
                    ""
                }} ")
            }
            sb.append(")")
        }
        pKeyword?.let { sb.append("${it.string.text} ") }
        pRuleCall?.let { sb.append("${it.referenceAbstractRuleRuleID.text} ") }
        pParenthesizedElement?.let {
            val branches = it.alternatives.conditionalBranchList
            sb.append("(")
            branches.forEach {
                writebrunch(it, sb)
                sb.append("${if (it != branches.last()) {
                    "| "
                } else {
                    ""
                }} ")
            }

            sb.append(") ")
        }
        return sb.toString()
    }


    private fun writeAssignableTerminal(assignableTerminal: XtextAssignableTerminal, sb: StringBuilder): String {
        val keyword = assignableTerminal.keyword
        val ruleCall = assignableTerminal.ruleCall
        val parenthesizedAssignableElement = assignableTerminal.parenthesizedAssignableElement
        val crossReference = assignableTerminal.crossReference
        keyword?.let { sb.append("${it.text} ") }
        ruleCall?.let { sb.append("${it.referenceAbstractRuleRuleID.text} ") }
        parenthesizedAssignableElement?.let {
            val branches = it.assignableAlternatives.assignableTerminalList
            sb.append("(")
            branches.forEach {
                writeAssignableTerminal(it, sb)
                sb.append("${if (it != branches.last()) {
                    "| "
                } else {
                    ""
                }} ")
            }
            sb.append(") ")
        }
        crossReference?.let {
            sb.append("REFERENCE_${it.typeRef.text}")
            it.crossReferenceableTerminal?.let { sb.append("_${it.text}") }
            sb.append(" ")
        }
        return sb.toString()
    }

    fun getRegexpAsString(rule: XtextTerminalRule): String {
        val sb = StringBuilder()
        var leaf = PsiTreeUtil.nextLeaf(rule.colon!!)
        loop@ while (leaf != null && PsiTreeUtil.isAncestor(rule, leaf, true)) {
            if (leaf is PsiComment) {
                leaf = PsiTreeUtil.nextLeaf(leaf) ?: break
            }
            val type = leaf.node.elementType.toString()
            when (type) {
                "STRING" -> {
                    var str = leaf.text.substring(1, leaf.text.lastIndex)

                    str = avoidRegexpSymbols(str)
                    sb.append(str)
                }
                "->" -> sb.append("(?s).*")
                ".." -> {
                    val ch = sb.get(sb.length - 1)
                    sb.deleteCharAt(sb.length - 1)
                    sb.append("[$ch-")

                    leaf = PsiTreeUtil.nextLeaf(leaf)
                    if (leaf != null) {
                        if (leaf.getNode()?.elementType.toString() == "WHITE_SPACE") {
                            leaf = PsiTreeUtil.nextLeaf(leaf)
                        }
                        if (leaf?.getNode()?.elementType.toString() == "STRING") {
                            var str = leaf?.text?.substring(1, leaf.text.lastIndex)
                            str = avoidRegexpSymbols(str ?: "")
                            sb.append("$str]")
                        } else return ""
                    } else return ""

                }
                "ID" -> {
                    val referencedTerminal = if (fileModel.getTerminalRuleByName(leaf.text) != null) getRegexpAsString(fileModel.getTerminalRuleByName(leaf.text).myRule)
                    else "ref_${leaf.text}"
                    sb.append(referencedTerminal)
                }
                "!" -> {
                    if (!writeNegatedRegexp(leaf, sb)) {
                        return ""
                    } else {
                        val parent = leaf.parent?.parent?.parent?.parent
                        if (parent != null) {
                            leaf = PsiTreeUtil.nextLeaf(parent)
                            continue@loop
                        } else return ""
                    }

                }
                "WHITE_SPACE" -> sb.append("")
                ";" -> break@loop
                else -> sb.append(leaf.text)
            }
            if (leaf != null) {
                leaf = PsiTreeUtil.nextLeaf(leaf)
            }
        }
        return sb.toString()
    }

    private fun writeNegatedRegexp(leaf: PsiElement, sb: StringBuilder): Boolean {
        sb.append("[^")
        val sibling = leaf.nextSibling
        if (sibling is XtextTerminalTokenElement) {
            var b = false
            sibling.characterRange?.let {

                b = writeNegatedRegexpRange(it, sb)
                sb.append("]")
                if (!b) {
                    sb.clear()
                    return false
                }
                return true

            }
            sibling.parenthesizedTerminalElement?.terminalAlternatives?.terminalGroupList?.forEach {

                if (it?.terminalTokenList?.size == 1) {
                    if (it.terminalTokenList.first().asterisk == null && it.terminalTokenList.first().quesMark == null && it.terminalTokenList.first().plus == null && it.terminalTokenList.first().terminalTokenElement.characterRange != null) {
                        it.terminalTokenList.first().terminalTokenElement.characterRange?.let { b = writeNegatedRegexpRange(it, sb) }

                    } else {
                        sb.clear()
                        return false
                    }
                    if (!b) {
                        sb.clear()
                        return false
                    }

                } else {
                    sb.clear()
                    return false
                }


            }
            if (b) {
                sb.append("]")
                return true
            }

        }
        sb.clear()
        return false
    }

    private fun writeNegatedRegexpRange(range: XtextCharacterRange, sb: StringBuilder): Boolean {

        val list = range.keywordList
        if (list.size == 1 && isOneCharacterString(list.first().text)) {
            sb.append(avoidRegexpSymbols(list.first().text.substring(1, list.first().text.lastIndex)))
            return true
        } else if (list.size == 2 && isOneCharacterString(list.get(0).text) && isOneCharacterString(list.get(1).text) && range.range != null) {
            sb.append("${avoidRegexpSymbols(list.first().text.substring(1, list.first().text.lastIndex))}-${avoidRegexpSymbols(list.get(1).text.replace("'", ""))}")
            return true
        }
        return false
    }

    private fun isOneCharacterString(string: String): Boolean {
        if (string.length == 3) return true
        else if (string.length == 4 && string.contains('\\')) return true
        else return false
    }

    private fun avoidRegexpSymbols(string: String): String {
        var sb = StringBuilder()
        val bannedSymbols = arrayOf('*', '.', '"')
        string.toCharArray().forEach {
            if (it in bannedSymbols) sb.append("\\$it")
            else sb.append(it)
        }
        return sb.toString()
    }

    fun culParserRules(rules: Array<XtextParserRule>): List<ParserRule> {
        var listOfRules = ArrayList<ParserRule>()
        rules.forEach {
            var rule_tmp = it

            val branches = it.alternatives?.conditionalBranchList
            branches?.forEach {
                rule_tmp = computeBranch(rule_tmp, it, listOfRules)
            }
            listOfRules.add(ParserRule(rule_tmp))
        }
        return listOfRules
    }

    fun computeBranch(rule: XtextParserRule, branch: XtextConditionalBranch, list: ArrayList<ParserRule>): XtextParserRule {
        var rule_temp = rule
        branch.unorderedGroup?.groupList?.forEach {
            it.abstractTokenList.forEach {
                it.action?.let {
                    if (it.validID != null) {
                        reconstructBranchWithAssignedAction(branch, it)
                    } else {
                        val pair = reconstructBranchWithAction(rule_temp, branch, it)
                        rule_temp = pair.first
                        list.add(ParserRule(pair.second))


                    }
                }


            }
        }
        return rule_temp
    }

    fun reconstructBranchWithAction(rule: XtextParserRule, branch: XtextConditionalBranch, action: XtextAction): Pair<XtextParserRule, XtextParserRule> {
        val ruleName = rule.ruleNameAndParams.text
        val newRuleName = "RuleFrom${ruleName}_${action.typeRef.text}"
        val newRule = XtextElementFactory.createParserRule("$newRuleName returns ${action.typeRef.text} : ${branchToString(branch)};")
        val changedMainRule = createRuleWithChangedBranch(rule, branch, newRuleName)
        return Pair(changedMainRule, newRule)
    }

    fun reconstructBranchWithAssignedAction(branch: XtextConditionalBranch, action: XtextAction) {

    }

    fun createRuleWithChangedBranch(rule: XtextParserRule, branch: XtextConditionalBranch, nameOfNewRule: String): XtextParserRule {
        var firstLeaf = PsiTreeUtil.getDeepestVisibleFirst(rule)
        val sb = StringBuilder()
        while (firstLeaf?.node?.elementType != XtextTypes.COLON) {
            sb.append(firstLeaf?.text)
            firstLeaf?.let { firstLeaf = PsiTreeUtil.nextLeaf(it) }

        }
        sb.append(": ")
        val branches = rule.alternatives?.conditionalBranchList
        branches?.let {
            it.forEach {
                if (it == branch) {
                    sb.append(nameOfNewRule)
                } else {
                    sb.append(branchToString(it))
                }
                if (it != branches.last()) sb.append("| ")

            }
        }
        sb.append(";")

        return XtextElementFactory.createParserRule(sb.toString())
    }

    fun branchToString(branch: XtextConditionalBranch): String {
        val sb = java.lang.StringBuilder()
        var leaf = PsiTreeUtil.getDeepestVisibleFirst(branch)
        while (leaf != null && PsiTreeUtil.isAncestor(branch, leaf, true)) {
            sb.append(leaf.text)
            leaf = PsiTreeUtil.nextLeaf(leaf)

        }
        return sb.toString()

    }
}