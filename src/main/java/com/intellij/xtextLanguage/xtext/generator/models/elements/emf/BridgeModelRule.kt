package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class BridgeModelRule(
        val name: String,
        val returnType: BridgeRuleType,
        val literalAssignments: List<AssignableObject>,
        val objectAssignments: List<AssignableObject>,
        val rewrites: List<Rewrite>,
        val simpleActions: List<BridgeSimpleAction>,
        val hasName: Boolean) {

    val importStrings: Set<String>

    init {
        val imports = mutableSetOf<String>()
        imports.add(this.returnType.path)
        literalAssignments.forEach { imports.add(it.returnType.path) }
        objectAssignments.forEach { imports.add(it.returnType.path) }
        rewrites.forEach { imports.add(it.returnType.path) }
        simpleActions.forEach { imports.add(it.returnType.path) }
        imports.remove("String")
        importStrings = imports
    }
}


