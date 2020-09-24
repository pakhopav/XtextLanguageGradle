package com.intellij.xtextLanguage.xtext.generator.models.elements

class CrossReferenceTarget(val superRuleName: String, val nameElements: List<String>) {
    val subRules = mutableListOf<TargetSubRule>()
}