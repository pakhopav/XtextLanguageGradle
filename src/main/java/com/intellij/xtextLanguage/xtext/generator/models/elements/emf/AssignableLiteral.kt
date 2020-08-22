package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

open class AssignableLiteral(val assignment: Assignment, val psiElementType: String, var returnType: String) {
    override fun equals(other: Any?): Boolean {
        if (other is AssignableLiteral) {
            return (assignment == other.assignment && psiElementType == other.psiElementType && returnType == other.returnType)
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