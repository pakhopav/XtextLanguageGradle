package com.intellij.xtextLanguage.xtext.generator

import com.intellij.xtextLanguage.xtext.psi.XtextTypeRef

class ReferenceElement(val name: String, val referenceType: String, val refetenceTarget: XtextTypeRef) {
    var targets = ArrayList<ParserRule>()


    override fun equals(other: Any?): Boolean {
        if ((other as? ReferenceElement)?.name == name) return true
        return false
    }
}
