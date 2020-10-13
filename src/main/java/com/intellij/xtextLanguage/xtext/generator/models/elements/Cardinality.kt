package com.intellij.xtextLanguage.xtext.generator.models.elements

enum class Cardinality {
    NONE, QUES, ASTERISK, PLUS;

    override fun toString(): String {
        return when (this) {
            QUES -> "?"
            ASTERISK -> "*"
            PLUS -> "+"
            else -> ""
        }
    }
}