package com.intellij.xtextLanguage.xtext.generator

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.psi.XtextTypeRef

class ReferenceElement(val name: String, val referenceType: String, val refetenceTarget: XtextTypeRef) {
    var targets: List<Class<out PsiElement>>? = null


    override fun equals(other: Any?): Boolean {
        if ((other as? ReferenceElement)?.name == name) return true
        return false
    }
}
