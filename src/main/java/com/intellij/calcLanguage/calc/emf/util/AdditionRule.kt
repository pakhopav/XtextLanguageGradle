package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.calcAddition
import com.intellij.calcLanguage.calc.psi.calcMultiplication
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import org.eclipse.emf.ecore.EObject

class AdditionRule : EmfBridgeRule {
    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        val context = pointer.context as calcAddition
        if (pointer is calcMultiplication) {
            if (context.multiplicationList.first() != pointer) {
                return { current: EObject, toAssign: EObject ->
                    if (CalcEmfBridgeUtil.ePACKAGE.plus.isInstance(current)) {
                        current.eSet(CalcEmfBridgeUtil.ePACKAGE.plus_Right, toAssign)
                    } else {
                        current.eSet(CalcEmfBridgeUtil.ePACKAGE.minus_Right, toAssign)
                    }
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        if (pointer.text == "+") return {
            val temp = CalcEmfBridgeUtil.eFACTORY.create(CalcEmfBridgeUtil.ePACKAGE.plus)
            temp.eSet(CalcEmfBridgeUtil.ePACKAGE.plus_Left, it)
            temp
        } else if (pointer.text == "-") return {
            val temp = CalcEmfBridgeUtil.eFACTORY.create(CalcEmfBridgeUtil.ePACKAGE.minus)
            temp.eSet(CalcEmfBridgeUtil.ePACKAGE.minus_Left, it)
            temp
        }
        return null
    }
}