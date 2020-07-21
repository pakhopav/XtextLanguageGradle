package com.intellij.calcLanguage.calc.emf.util

import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject

class ImportRule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): ((EObject) -> Unit)? {
        return null
    }

    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {

        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        return null
    }

    override fun createObject(): EObject {
        return eFACTORY.create(ePACKAGE.import)
    }
}