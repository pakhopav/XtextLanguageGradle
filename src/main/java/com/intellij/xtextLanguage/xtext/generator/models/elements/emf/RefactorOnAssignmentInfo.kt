package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class RefactorOnAssignmentInfo(val originRuleName: String, val privateRuleName: String) {
    val duplicateRuleNames = mutableListOf<String>()

}