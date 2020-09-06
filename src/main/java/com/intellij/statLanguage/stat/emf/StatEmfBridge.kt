package com.intellij.statLanguage.stat.emf

import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.statLanguage.stat.psi.StatFile
import com.intellij.statLanguage.stat.psi.StatStatemachine
import com.intellij.xtextLanguage.xtext.emf.EmfBridge
import org.eclipse.emf.ecore.EObject

class StatEmfBridge : EmfBridge {
    override fun createEmfModel(file: PsiFile): EObject? {
        if (file is StatFile) {
            val filePsiRoot = PsiTreeUtil.findChildOfType(file, StatStatemachine::class.java)
            filePsiRoot?.let {
                val emfCreator = StatEmfCreator()
                return emfCreator.createModel(it)
            }
        }
        return null
    }
}