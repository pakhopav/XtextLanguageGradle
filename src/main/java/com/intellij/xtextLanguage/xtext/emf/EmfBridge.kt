package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiFile
import org.eclipse.emf.ecore.EObject

interface EmfBridge {
    fun createEmfModel(file: PsiFile): EObject?
    fun completeRawModel(rawModel: EObject): EObject
}