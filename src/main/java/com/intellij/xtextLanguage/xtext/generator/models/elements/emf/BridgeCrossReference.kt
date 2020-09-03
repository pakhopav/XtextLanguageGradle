package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class BridgeCrossReference(val assignment: Assignment,
                           val container: BridgeRuleType,
                           val target: BridgeRuleType,
                           val psiElementName: String) {
}