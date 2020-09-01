package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class BridgeSimpleAction(val className: String, val psiElementType: String) {
    override fun equals(other: Any?): Boolean {
        if (other is BridgeSimpleAction) {
            return (className == other.className && psiElementType == other.psiElementType)
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = className.hashCode()
        hash = hash * 7 + psiElementType.hashCode()
        return hash
    }
}