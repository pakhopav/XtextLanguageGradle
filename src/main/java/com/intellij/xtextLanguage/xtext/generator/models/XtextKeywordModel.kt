package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.Keyword
import com.intellij.xtextLanguage.xtext.generator.visitors.XtextVisitor
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractRule
import com.intellij.xtextLanguage.xtext.psi.XtextKeyword

class XtextKeywordModel(abstractRules: List<XtextAbstractRule>) {

    companion object {
        val KEYWORDS = mapOf(
                "," to "COMMA",
                "(" to "L_BRACKET",
                ")" to "R_BRACKET",
                "@" to "AT_SIGN",
                ";" to "SEMICOLON",
                "<" to "L_ANGLE_BRACKET",
                ">" to "R_ANGLE_BRACKET",
                "{" to "L_BRACE",
                "}" to "R_BRACE",
                "[" to "L_SQUARE_BRACKET",
                "]" to "R_SQUARE_BRACKET",
                "&" to "AMPERSAND",
                "!" to "ACX_MARK",
                ":" to "COLON",
                "*" to "ASTERISK",
                "=" to "EQUALS",
                "=>" to "PRED",
                "->" to "WEAK_PRED",
                "|" to "PIPE",
                "+" to "PLUS",
                "?" to "QUES_MARK",
                ".." to "RANGE",
                "." to "DOT",
                "+=" to "PLUS_EQUALS",
                "?=" to "QUES_EQUALS",
                "EOF" to "EOF_KEY",
                "::" to "COLONS",
                "-" to "MINUS",
                "/" to "SLASH",
                "#" to "HASH"
        )
    }


    val keywords: List<Keyword>
    val keywordsForParserDefinitionFile: List<Keyword>

    var i = 0

    init {
        val list = mutableListOf<String>()
        abstractRules.forEach {
            list.addAll(KeywordsFinder.getKeywordsOfAbstractRule(it))
        }
        val listOfKeywords = mutableListOf<Keyword>()
        val listOfKeywordsForParserDefinition = mutableListOf<Keyword>()

        list.distinct().map { it.slice(1 until it.length - 1) }.forEach {
            listOfKeywords.add(Keyword(it, createKeywordName(it)))
            if (it.matches(Regex("[a-zA-Z]+"))) {
                listOfKeywordsForParserDefinition.add(Keyword(it, it.toUpperCase() + "_KEYWORD"))
            }
        }

        keywords = listOfKeywords
        keywordsForParserDefinitionFile = listOfKeywordsForParserDefinition


    }

    fun createKeywordName(text: String): String {
        val postfix = "_KEYWORD"
        KEYWORDS.get(text)?.let { return it + postfix }
        if (text.matches(Regex("[a-zA-Z0-9_]+"))) return text.toUpperCase() + postfix
        else return "KEYWORD_${i++}"
    }

    class KeywordsFinder : XtextVisitor() {
        val keywordList = mutableListOf<String>()

        companion object {
            fun getKeywordsOfAbstractRule(rule: XtextAbstractRule): List<String> {

                val visitor = KeywordsFinder()
                visitor.visitAbstractRule(rule)
                return visitor.keywordList
            }
        }

        override fun visitKeyword(o: XtextKeyword) {
            keywordList.add(o.text)
        }
    }
}