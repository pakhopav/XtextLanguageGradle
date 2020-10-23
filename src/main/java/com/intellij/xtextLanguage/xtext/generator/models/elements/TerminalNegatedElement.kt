package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextNegatedToken

class TerminalNegatedElement(override val psiElement: XtextNegatedToken) : TerminalRuleElement(psiElement) {
    override var assignment = ""
    val characterRanges: List<TerminalRangeElement>

    init {
        val ranges = mutableListOf<TerminalRangeElement>()
        psiElement.terminalTokenElement.characterRange?.let {
            ranges.add(TerminalRangeElement(it))
        }
        psiElement.terminalTokenElement.parenthesizedTerminalElement?.let {
            it.terminalAlternatives.terminalGroupList.forEach {
                if (it.terminalTokenList.size > 1) {
                    throw Exception("Wrong negated terminal")

                } else {
                    it.terminalTokenList.first().terminalTokenElement.characterRange?.let {
                        ranges.add(TerminalRangeElement(it))
                    } ?: throw Exception("Wrong negated terminal")

                }
            }
        }
        characterRanges = ranges
    }

    override fun getFlexName(): String {
        return getBnfName()
    }

    override fun getBnfName(): String {
        refactoredName?.let { return it }
        val sb = StringBuilder()
        sb.append("[^")
        characterRanges.forEach {
            sb.append(it.getBnfName())
        }
        sb.append(("]"))
        return sb.toString()
    }

}