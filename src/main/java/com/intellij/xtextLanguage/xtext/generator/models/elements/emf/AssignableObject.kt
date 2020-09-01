package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

open class AssignableObject(val assignment: Assignment, val psiElementType: String, val returnType: String) {
    override fun equals(other: Any?): Boolean {
        if (other is AssignableObject) {
            return (psiElementType == other.psiElementType && assignment == other.assignment && returnType == other.returnType)
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = assignment.hashCode()
        hash = hash * 7 + psiElementType.hashCode()
        hash = hash * 7 + returnType.hashCode()
        return hash
    }

}