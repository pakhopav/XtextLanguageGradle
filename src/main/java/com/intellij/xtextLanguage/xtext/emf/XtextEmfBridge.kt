package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.bridge.EmfBridge
import com.intellij.xtextLanguage.xtext.psi.XtextFile
import com.intellij.xtextLanguage.xtext.psi.XtextGrammar
import org.eclipse.emf.ecore.EObject

class XtextEmfBridge : EmfBridge {
    override fun createEmfModel(file: PsiFile): EObject? {
        if (file is XtextFile) {
            val filePsiRoot = PsiTreeUtil.findChildOfType(file, XtextGrammar::class.java)
            filePsiRoot?.let {
                val emfCreator = XtextEmfCreator()
                return emfCreator.createModel(it)
            }
        }
        return null
    }
}