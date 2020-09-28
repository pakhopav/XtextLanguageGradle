package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

data class Rewrite(val newObjectType: BridgeRuleType, val assignment: Assignment, val psiElementType: String,
                   val returnType: BridgeRuleType) {


}