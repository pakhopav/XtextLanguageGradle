package com.intellij.calcLanguage.calc.emf

import arithmetics.Module
import com.intellij.calcLanguage.calc.psi.calcFile
import com.intellij.calcLanguage.calc.psi.calcModule
import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.emf.EmfBridge

class CalcEmfBridge : EmfBridge {


    override fun createEmfModel(file: PsiFile): Module? {
        if (file is calcFile) {
            val filePsiRoot = PsiTreeUtil.findChildOfType(file, calcModule::class.java)
            filePsiRoot?.let {
                val visitor = CalcEmfVisitor()
                return visitor.createModel(it)
            }
        }
        return null
    }
}