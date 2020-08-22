package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

class Assignment(val text: String, val type: AssignmentType) {
    override fun equals(other: Any?): Boolean {
        if (other is Assignment) {
            if (text != other.text) return false
            if (type != other.type) return false
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        var hash = text.hashCode()
        hash += type.ordinal
        return hash
    }
}