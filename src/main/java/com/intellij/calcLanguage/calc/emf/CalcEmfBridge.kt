package com.intellij.calcLanguage.calc.emf

import com.intellij.calcLanguage.calc.psi.calcFile
import com.intellij.calcLanguage.calc.psi.calcModule
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.emf.EmfBridge
import org.eclipse.emf.ecore.EObject

class CalcEmfBridge : EmfBridge {


    override fun createEmfModel(file: PsiFile): EObject? {
        if (file is calcFile) {
            val filePsiRoot = PsiTreeUtil.findChildOfType(file, calcModule::class.java)
            filePsiRoot?.let {
                val emfCreator = CalcEmfCreator()
                return emfCreator.createModel(it)
            }
        }
        return null
    }
}