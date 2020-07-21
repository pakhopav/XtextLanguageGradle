package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.calcTypes
import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject

class EvaluationRule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): ((EObject) -> Unit)? {
        return null
    }

    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        if (pointer.node.elementType == calcTypes.EXPRESSION) {
            return { current: EObject, toAssign: EObject ->
                current.eSet(ePACKAGE.evaluation_Expression, toAssign)
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        return null
    }

    override fun createMyEmfObject(): EObject {
        return eFACTORY.create(ePACKAGE.evaluation)
    }
}