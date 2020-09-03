package com.intellij.calcLanguage.calc.emf

import com.intellij.calcLanguage.calc.psi.CalcFile
import com.intellij.calcLanguage.calc.psi.CalcModule
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.emf.EmfBridge
import org.eclipse.emf.ecore.EObject

class CalcEmfBridge : EmfBridge {
    override fun createEmfModel(file: PsiFile): EObject? {
        if (file is CalcFile) {
            val filePsiRoot = PsiTreeUtil.findChildOfType(file, CalcModule::class.java)
            filePsiRoot?.let {
                val emfCreator = CalcEmfCreator()
                return emfCreator.createModel(it)
            }
        }
        return null
    }
}