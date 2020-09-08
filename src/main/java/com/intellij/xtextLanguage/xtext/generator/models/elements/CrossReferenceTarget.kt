package com.intellij.xtextLanguage.xtext.generator.models.elements

class CrossReferenceTarget(val superRuleName: String) {
    val subRuleNames = mutableListOf<String>()
}