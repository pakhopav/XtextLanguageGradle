package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class BridgeSimpleAction(val className: String, val psiElementName: String) {
    override fun equals(other: Any?): Boolean {
        if (other is BridgeSimpleAction) {
            return (className == other.className && psiElementName == other.psiElementName)
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = className.hashCode()
        hash = hash * 7 + psiElementName.hashCode()
        return hash
    }
}