package com.intellij.xtextLanguage.xtext.generator.models.elements.emf

data class Assignment(val text: String, val type: AssignmentType) {

    companion object {
        fun fromString(string: String): Assignment {
            val assignmentPenultChar = string[string.length - 2]
            if (assignmentPenultChar == '+') {
                return Assignment(string.slice(0..string.length - 3), AssignmentType.PLUS_EQUALS)
            } else if (assignmentPenultChar == '?') {
                return Assignment(string.slice(0..string.length - 3), AssignmentType.QUESTION_EQUALS)
            } else {
                return Assignment(string.slice(0..string.length - 2), AssignmentType.EQUALS)
            }
        }

    }

}