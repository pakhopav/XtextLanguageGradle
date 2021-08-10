package com.intellij.xtext.bridge

import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature

interface LiteralAssignment {
    fun assign(obj: EObject, literal: PsiElement): EStructuralFeature
}