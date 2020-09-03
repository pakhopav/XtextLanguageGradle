package com.intellij.xtextLanguage.xtext.generator.models

import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.BridgeCrossReference
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.BridgeModelRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.emf.RefactorOnAssignmentInfo

class BridgeModel(val bridgeRules: List<BridgeModelRule>,
                  val rootRuleName: String,
                  val crossReferences: List<BridgeCrossReference>,
                  val assignmentRefactoringsInfos: List<RefactorOnAssignmentInfo>) {
}