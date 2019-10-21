package com.intellij.xtextLanguage.xtext.generator.models.elements

import com.intellij.xtextLanguage.xtext.psi.XtextNegatedToken

class TerminalNegatedElement(override val psiElement: XtextNegatedToken) : TerminalRuleElement(psiElement) {
    override fun getFlexName(): String {
        return getBnfName()
    }

    override fun getBnfName(): String {
        psiElement.terminalTokenElement.characterRange?.let {
            if (it.keywordList.size == 2) {
                return "[^${TerminalRangeElement(it).getBnfName().substring(1, TerminalRangeElement(it).getBnfName().length - 1)}]"
            } else {
                return "[^${it.keywordList.get(0)}]"
            }
        }
        psiElement.terminalTokenElement.parenthesizedTerminalElement?.let {
            val sb = StringBuilder()
            sb.append("[^")
            it.terminalAlternatives.terminalGroupList.forEach {
                if (it.terminalTokenList.size > 1) {
                    return ""
                } else {
                    it.terminalTokenList.first().terminalTokenElement.characterRange?.let {
                        it.keywordList.forEach {
                            if (!isOneCharacterString(it.string.text)) {
                                return ""
                            }
                        }
                        sb.append(TerminalKeywordElement(it.keywordList.get(0)).getBnfName())
                        if (it.keywordList.size > 1) {
                            sb.append("-" + TerminalKeywordElement(it.keywordList.get(1)).getBnfName())
                        }

                    } ?: return ""
                }
            }
            sb.append("]")
            return sb.toString()
        }
        return ""
    }

    private fun isOneCharacterString(string: String): Boolean {
        if (string.length == 3) return true
        else if (string.length == 4 && string.contains('\\')) return true
        else return false
    }
}