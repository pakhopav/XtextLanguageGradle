package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiElement
import com.intellij.util.containers.stream


open class BridgeUtil {
    fun hasChildWithText(element: PsiElement, text: String): Boolean {
        return element.children.stream().filter { it.text == text }.count() > 0
    }

}