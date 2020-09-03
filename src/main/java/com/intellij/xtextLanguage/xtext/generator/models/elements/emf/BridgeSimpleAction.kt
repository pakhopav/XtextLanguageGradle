package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class BridgeSimpleAction(val returnType: BridgeRuleType, val psiElementType: String) {

    override fun equals(other: Any?): Boolean {
        if (other is BridgeSimpleAction) {
            return (returnType == other.returnType && psiElementType == other.psiElementType)
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = returnType.hashCode()
        hash = hash * 7 + psiElementType.hashCode()
        return hash
    }
}