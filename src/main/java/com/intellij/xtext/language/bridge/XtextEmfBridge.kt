package com.intellij.xtext.language.bridge

import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtext.bridge.EmfBridge
import com.intellij.xtext.language.psi.XtextFile
import com.intellij.xtext.language.psi.XtextGrammar
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