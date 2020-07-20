package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.calcREFERENCEModuleID
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import org.eclipse.emf.ecore.EObject

class ImportRule : EmfBridgeRule {
    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        if (pointer is calcREFERENCEModuleID) {
            return { current: EObject, toAssign: EObject ->
                current.eSet(CalcEmfBridgeUtil.ePACKAGE.import_Module, toAssign)
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        return null
    }
}