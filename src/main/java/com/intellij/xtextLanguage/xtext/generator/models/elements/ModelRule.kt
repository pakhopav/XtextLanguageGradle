package com.intellij.xtextLanguage.xtext.generator.models.elements


abstract class ModelRule() {
    abstract val returnType: String
    abstract val name: String
    abstract var isDataTypeRule: Boolean
    abstract var alternativeElements: MutableList<out RuleElement>

}