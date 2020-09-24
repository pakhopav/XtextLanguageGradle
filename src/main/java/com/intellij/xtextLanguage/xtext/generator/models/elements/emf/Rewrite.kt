package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class Rewrite(val newObjectType: BridgeRuleType, val assignment: Assignment, val psiElementType: String, val returnType: BridgeRuleType, val single: Boolean = false) {
    override fun equals(other: Any?): Boolean {
        if (other is Rewrite) {
            return (newObjectType == other.newObjectType && assignment == other.assignment && psiElementType == other.psiElementType)
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = assignment.hashCode()
        hash = hash * 7 + newObjectType.hashCode()
        hash = hash * 7 + psiElementType.hashCode()
        return hash
    }
}