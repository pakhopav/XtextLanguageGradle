package com.intellij.xtext.bridge

import com.intellij.psi.PsiFile
import org.eclipse.emf.ecore.EObject

interface EmfBridge {
    fun createEmfModel(file: PsiFile): EObject?

}