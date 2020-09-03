package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class BridgeRuleType(type: String, val prefix: String) {
    val name = type.split(".").last()
    val path = type

}