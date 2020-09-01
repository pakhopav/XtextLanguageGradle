package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class BridgeModelRule(
        val name: String,
        val type: String,
        val literalAssignments: List<AssignableObject>,
        val objectAssignments: List<AssignableObject>,
        val rewrites: List<Rewrite>,
        val simpleActions: List<BridgeSimpleAction>,
        val hasName: Boolean
)

