package com.intellij.xtext.metamodel.elements

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