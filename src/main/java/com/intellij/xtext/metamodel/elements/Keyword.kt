package com.intellij.xtext.metamodel.elements

data class Keyword(val keyword: String, val name: String) {
    val highlighted: Boolean = keyword.matches(Regex("[a-zA-Z]+"))

}