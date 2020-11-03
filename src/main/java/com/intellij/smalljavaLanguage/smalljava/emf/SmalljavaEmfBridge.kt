package com.intellij.smalljavaLanguage.smalljava.emf

import com.intellij.psi.PsiFile
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaFile
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJProgram
import com.intellij.xtextLanguage.xtext.emf.EmfBridge
import org.eclipse.emf.ecore.EObject

class SmalljavaEmfBridge : EmfBridge {
    override fun createEmfModel(file: PsiFile): EObject? {
        if (file is SmalljavaFile) {
            val filePsiRoot = PsiTreeUtil.findChildOfType(file, SmalljavaSJProgram::class.java)
            filePsiRoot?.let {
                val emfCreator = SmalljavaEmfCreator()
                return emfCreator.createModel(it)
            }
        }
        return null
    }
}