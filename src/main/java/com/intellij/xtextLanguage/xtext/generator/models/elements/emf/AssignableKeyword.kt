package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class AssignableKeyword(assignment: Assignment, val keywordText: String) : AssignableObject(assignment, keywordText, BridgeRuleType("String", "")) {
}