package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject

interface EmfBridgeRule {

    fun findLiteralAssignment(pointer: PsiElement): ((EObject) -> Unit)?

    fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)?

    fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)?

    fun createMyEmfObject(): EObject
}