package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject

interface EmfBridgeRule {

    fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)?

    fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)?
}