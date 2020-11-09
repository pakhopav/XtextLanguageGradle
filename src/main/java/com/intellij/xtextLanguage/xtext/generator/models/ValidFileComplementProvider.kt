package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.ValidFileComplement
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.*
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesIsInstance
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.impl.*
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ValidFileComplementProvider(val context: MetaContext) {
    private val complementsMap = mutableMapOf<TreeRule, ValidFileComplement>()
    private val allRules = mutableListOf<TreeRule>()
    private val root = context.rules.first()

    init {
        allRules.addAll(context.rules)
        allRules.addAll(context.terminalRules.map { it as TreeRule })
        initComplementMap()
    }

    /**
     *
     */
    fun getComplementIfPossible(rule: TreeRule): ValidFileComplement? {
        return complementsMap.get(rule)
    }


    private fun initComplementMap() {
        allRules.filter { it !is TreeFragmentRule }.forEach { rule ->
            if (rule == root) {
                complementsMap.put(rule, ValidFileComplement("", ""))
                return@forEach
            }
            val complement = createComplement(rule)
            complement?.let {
                complementsMap.put(rule, complement)
            }
        }
    }

    private fun createComplement(rule: TreeRule): ValidFileComplement? {
        val prefixBuilder = StringBuilder()
        val suffixBuilder = StringBuilder()
        val searchingResult = a(root, rule, prefixBuilder, suffixBuilder, false)
        if (searchingResult) {
            return ValidFileComplement(prefixBuilder.toString(), suffixBuilder.toString())
        }
        return null
    }

    private fun a(node: TreeNode, toSearch: TreeRule, pPrefixBuilder: StringBuilder, pSuffixBuilder: StringBuilder, ruleFound: Boolean): Boolean {
        var found = ruleFound
        val optionalNode = isOptional(node)
        val prefixBuilder = StringBuilder().append(pPrefixBuilder.toString())
        val suffixBuilder = StringBuilder().append(pSuffixBuilder.toString())
        var currentBuilder = if (found) suffixBuilder else prefixBuilder
        node.children.forEach { child ->
            if (found && isOptional(child)) {
                return@forEach
            }
            when (child) {
                is TreeKeyword -> {
                    var keywordString = child.getBnfName()
                    keywordString = keywordString.slice(1..keywordString.length - 2)
                    currentBuilder.append(keywordString + " ")
                }
                is TreeRuleCall, is TreeCrossReference -> {
                    child as TreeLeaf
                    val calledRule = allRules.firstOrNull { it.name == child.getBnfName() }
                    assertNotNull(calledRule)
                    if (calledRule == toSearch) {
                        val firstEncounter = !found
                        found = true
                        currentBuilder = suffixBuilder
                        if (firstEncounter) return@forEach
                    }

                    when (calledRule) {
                        is TreeParserRule, is TreeFragmentRule -> {
                            val recursiveCallResult = a(calledRule, toSearch, prefixBuilder, suffixBuilder, found)
                            if (recursiveCallResult && !found) {
                                found = true
                                currentBuilder = suffixBuilder
                            }
                        }
                        is TreeEnumRule -> {
                            currentBuilder.append(createDummyString(calledRule) + " ")
                        }
                        is TreeTerminalRule -> {
                            currentBuilder.append(createDummyString(calledRule) + " ")
                        }
                    }
                }
                is TreeGroup -> {
                    val recursiveCallResult = a(child, toSearch, prefixBuilder, suffixBuilder, found)
                    if (recursiveCallResult && !found) {
                        found = true
                        currentBuilder = suffixBuilder
                    }
                }
                is TreeBranch -> {
                    val recursiveCallResult = a(child, toSearch, prefixBuilder, suffixBuilder, found)
                    if (recursiveCallResult && !found) {
                        found = true
                        currentBuilder = suffixBuilder
                    }
                }
            }
        }
        if (!found) {
            if (!optionalNode) {
                pPrefixBuilder.clear().append(prefixBuilder.toString())
                pSuffixBuilder.clear().append(suffixBuilder.toString())
            }
            return false
        }
        pPrefixBuilder.clear().append(prefixBuilder.toString())
        pSuffixBuilder.clear().append(suffixBuilder.toString())
        return true
    }

    private fun createDummyString(enumRule: TreeEnumRule): String {
        val firstKeyword = enumRule.filterNodesIsInstance(TreeKeyword::class.java).firstOrNull()
        assertNotNull(firstKeyword)
        return firstKeyword.getBnfName()
    }

    private fun createDummyString(terminalRule: TreeTerminalRule): String {
        val stringBuilder = StringBuilder()
        createTerminalRuleDummy(terminalRule, stringBuilder)
        return stringBuilder.toString()
    }

    private fun createTerminalRuleDummy(node: TreeNode, stringBuilder: StringBuilder) {
        node.children.forEach { child ->
            if (isOptional(child)) return@forEach
            computeForTerminalRuleNode(child, stringBuilder)
        }
    }

    private fun computeForTerminalRuleNode(node: TreeNode, stringBuilder: StringBuilder) {
        when (node) {
            is TreeTerminalKeyword -> {
                stringBuilder.append(node.getString())
            }
            is TreeTerminalRange -> {
                val terminalKeyword = node.children.first()
                stringBuilder.append(terminalKeyword.getString())
            }
            is TreeTerminalRuleCall -> {
                createTerminalRuleDummy(node.called!!, stringBuilder)
            }
            is TreeTerminalUntil -> {
                node.children.firstOrNull()?.let {
                    assertTrue(it is TreeTerminalKeyword)
                    stringBuilder.append(it.getString())
                }
            }
            is TreeTerminalNegatedToken -> {
                stringBuilder.append(createValidStringForNegatedToken(node))
            }
            is TreeTerminalWildcard -> {
                stringBuilder.append("a")
            }
            is TreeTerminalGroup -> {
                createTerminalRuleDummy(node, stringBuilder)
            }
            is TreeTerminalBranch -> {
                computeForTerminalRuleNode(node.children.first(), stringBuilder)
            }
        }
    }

    private fun createValidStringForNegatedToken(negatedToken: TreeTerminalNegatedToken): String {
        var stringToReturn = ""
        val tokenRegex = negatedToken.toRegex()
        for (i in 0..256) {
            val bruteForceChar = i.toChar().toString()
            if (tokenRegex.matches(bruteForceChar)) {
                stringToReturn = bruteForceChar
                break
            }
        }
        assertTrue(stringToReturn.isNotEmpty())
        return stringToReturn
    }


    private fun isOptional(node: TreeNode): Boolean {
        val nodeCardinality = node.cardinality
        if (nodeCardinality == Cardinality.NONE || nodeCardinality == Cardinality.PLUS) {
            val parent = (node as TreeNodeImpl).parent
            if (parent is TreeBranch) {
                return parent.children.first() != node
            }
            return false
        }
        return true
    }

    enum class ReturnState {
        FOUND, NOT_FOUND, CYCLE
    }
}