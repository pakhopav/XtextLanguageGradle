package com.intellij.xtext.metamodel

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtext.language.psi.*
import com.intellij.xtext.metamodel.model.elements.Keyword

class KeywordsCreator() {

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
                "::" to "COLONS",
                "-" to "MINUS",
                "/" to "SLASH",
                "#" to "HASH"
        )
    }


    var i = 1

    fun createKeywords(xtextFiles: List<XtextFile>): List<Keyword> {
        val abstractRules = mutableListOf<XtextAbstractRule>()
        xtextFiles.forEach {
            abstractRules.addAll(PsiTreeUtil.findChildrenOfType(it, XtextAbstractRule::class.java))
        }
        val keywordStrings = mutableListOf<String>()
        abstractRules.forEach {
            keywordStrings.addAll(KeywordsFinder.getKeywordsOfAbstractRule(it))
        }
        val listOfKeywords = mutableListOf<Keyword>()
        keywordStrings.distinct().map { it.slice(1 until it.length - 1) }.forEach {
            listOfKeywords.add(Keyword(it, createKeywordName(it, listOfKeywords)))
            if (it.matches(Regex("[a-zA-Z]+"))) {
            }
        }
        return listOfKeywords
    }

    private fun createKeywordName(text: String, keywords: List<Keyword>): String {
        val postfix = "_KEYWORD"
        KEYWORDS.get(text)?.let { return it + postfix }
        if (text.matches(Regex("[a-zA-Z0-9_]+"))) {
            val name = text.toUpperCase() + postfix
            if (isUnique(name, keywords)) return name
        }
        return generateUniqueName()
    }

    private fun generateUniqueName(): String {
        return "KEYWORD_${i++}"
    }

    private fun isUnique(name: String, keywords: List<Keyword>): Boolean {
        return !keywords.map { it.name }.any { it == name }
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

        override fun visitKeyword(keyword: XtextKeyword) {
            keywordList.add(keyword.text)
        }

        override fun visitEnumLiteralDeclaration(literalDeclaration: XtextEnumLiteralDeclaration) {
            literalDeclaration.keyword?.let {
                keywordList.add(it.text)
            } ?: kotlin.run {
                keywordList.add(literalDeclaration.referenceeEnumLiteralID.text)
            }
        }

        override fun visitPredicatedKeyword(predicatedKeyword: XtextPredicatedKeyword) {
            keywordList.add(predicatedKeyword.string.text)
        }
    }
}