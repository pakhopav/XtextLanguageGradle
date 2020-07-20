package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.calcMultiplication
import com.intellij.calcLanguage.calc.psi.calcPrimaryExpression
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import org.eclipse.emf.ecore.EObject

class MultiplicationRule : EmfBridgeRule {
    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        val context = pointer.context as calcMultiplication
        if (pointer is calcPrimaryExpression) {
            if (context.primaryExpressionList.first() != pointer) {
                return { current: EObject, toAssign: EObject ->
                    if (CalcEmfBridgeUtil.ePACKAGE.multi.isInstance(current)) {
                        current.eSet(CalcEmfBridgeUtil.ePACKAGE.multi_Right, toAssign)
                    } else {
                        current.eSet(CalcEmfBridgeUtil.ePACKAGE.div_Right, toAssign)
                    }
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        if (pointer.text == "*") return {
            val temp = CalcEmfBridgeUtil.eFACTORY.create(CalcEmfBridgeUtil.ePACKAGE.multi)
            temp.eSet(CalcEmfBridgeUtil.ePACKAGE.multi_Left, it)
            temp
        } else if (pointer.text == "/") return {
            val temp = CalcEmfBridgeUtil.eFACTORY.create(CalcEmfBridgeUtil.ePACKAGE.div)
            temp.eSet(CalcEmfBridgeUtil.ePACKAGE.div_Left, it)
            temp
        }
        return null
    }
}