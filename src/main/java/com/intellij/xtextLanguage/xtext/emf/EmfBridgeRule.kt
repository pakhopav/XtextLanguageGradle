package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EClass
import org.eclipse.emf.ecore.EObject

interface EmfBridgeRule {

    fun findAction(pointer: PsiElement): EClass?

    fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment?

    fun findObjectAssignment(pointer: PsiElement): ObjectAssignment?

    fun findRewrite(pointer: PsiElement): Rewrite?

    fun createObject(): EObject
}