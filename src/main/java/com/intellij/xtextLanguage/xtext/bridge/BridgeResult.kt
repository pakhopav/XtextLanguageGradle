package com.intellij.xtextLanguage.xtext.bridge

import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject

class BridgeResult(val emfRoot: EObject, val map: Map<PsiElement, Any>) {
}