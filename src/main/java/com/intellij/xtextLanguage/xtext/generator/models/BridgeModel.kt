package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.BridgeCrossReference
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.BridgeModelRule

class BridgeModel(val bridgeRules: List<BridgeModelRule>, val crossReferences: List<BridgeCrossReference>) {
}