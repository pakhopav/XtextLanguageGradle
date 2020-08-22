package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class AssignableObject(val assignment: Assignment, val psiElementType: String) {
    override fun equals(other: Any?): Boolean {
        if (other is AssignableObject) {
            return (psiElementType == other.psiElementType && assignment == other.assignment)
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = assignment.hashCode()
        hash = hash * 7 + psiElementType.hashCode()
        return hash
    }

}