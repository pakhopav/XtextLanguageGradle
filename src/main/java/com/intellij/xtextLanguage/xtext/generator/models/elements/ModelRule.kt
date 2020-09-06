package com.intellij.xtextLanguage.xtext.generator.models.elements


abstract class ModelRule {
    abstract val returnTypeText: String
    abstract val name: String
    abstract var isDataTypeRule: Boolean
    abstract val alternativeElements: MutableList<RuleElement>


}