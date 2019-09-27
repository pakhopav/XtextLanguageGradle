package com.intellij.xtextLanguage.xtext.generator

class ReferenceElement(val name: String, val referenceType: String) {
    override fun equals(other: Any?): Boolean {
        if ((other as? ReferenceElement)?.name == name) return true
        return false
    }
}
