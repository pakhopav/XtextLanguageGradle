package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.calcAddition
import com.intellij.calcLanguage.calc.psi.calcMultiplication
import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject

class AdditionRule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): ((EObject) -> Unit)? {
        return null
    }

    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        val context = pointer.context as calcAddition
        if (pointer is calcMultiplication) {
            if (context.multiplicationList.first() != pointer) {
                return { current: EObject, toAssign: EObject ->
                    if (ePACKAGE.plus.isInstance(current)) {
                        current.eSet(ePACKAGE.plus_Right, toAssign)
                    } else {
                        current.eSet(ePACKAGE.minus_Right, toAssign)
                    }
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        if (pointer.text == "+") return {
            val temp = eFACTORY.create(ePACKAGE.plus)
            temp.eSet(ePACKAGE.plus_Left, it)
            temp
        } else if (pointer.text == "-") return {
            val temp = eFACTORY.create(ePACKAGE.minus)
            temp.eSet(ePACKAGE.minus_Left, it)
            temp
        }
        return null
    }

    override fun createObject(): EObject {
        return eFACTORY.create(ePACKAGE.expression)
    }
}