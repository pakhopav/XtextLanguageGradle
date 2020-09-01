package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class Rewrite(val className: String, val assignment: Assignment, val psiElementType: String, val returnType: String) {
    override fun equals(other: Any?): Boolean {
        if (other is Rewrite) {
            return (className == other.className && assignment == other.assignment && psiElementType == other.psiElementType)
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = assignment.hashCode()
        hash = hash * 7 + className.hashCode()
        hash = hash * 7 + psiElementType.hashCode()
        return hash
    }
}