package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.calcMultiplication
import com.intellij.calcLanguage.calc.psi.calcPrimaryExpression
import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject

class MultiplicationRule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): ((EObject) -> Unit)? {
        return null
    }

    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        val context = pointer.context as calcMultiplication
        if (pointer is calcPrimaryExpression) {
            if (context.primaryExpressionList.first() != pointer) {
                return { current: EObject, toAssign: EObject ->
                    if (ePACKAGE.multi.isInstance(current)) {
                        current.eSet(ePACKAGE.multi_Right, toAssign)
                    } else {
                        current.eSet(ePACKAGE.div_Right, toAssign)
                    }
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        if (pointer.text == "*") return {
            val temp = eFACTORY.create(ePACKAGE.multi)
            temp.eSet(ePACKAGE.multi_Left, it)
            temp
        } else if (pointer.text == "/") return {
            val temp = eFACTORY.create(ePACKAGE.div)
            temp.eSet(ePACKAGE.div_Left, it)
            temp
        }
        return null
    }

    override fun createObject(): EObject {
        return eFACTORY.create(ePACKAGE.expression)
    }
}