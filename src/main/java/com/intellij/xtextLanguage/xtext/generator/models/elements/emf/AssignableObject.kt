package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

open class AssignableObject(val assignment: Assignment, val psiElementTypes: List<String>, val returnType: BridgeRuleType) {
    override fun equals(other: Any?): Boolean {
        if (other is AssignableObject) {

            return (psiElementTypes.equals(other.psiElementTypes) && assignment == other.assignment && returnType == other.returnType)
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = assignment.hashCode()
        hash = hash * 7 + psiElementTypes.hashCode()
        hash = hash * 7 + returnType.hashCode()
        return hash
    }

}