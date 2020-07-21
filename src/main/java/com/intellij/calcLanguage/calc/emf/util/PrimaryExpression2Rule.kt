package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.calcTypes
import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject
import java.math.BigDecimal

class PrimaryExpression2Rule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): ((EObject) -> Unit)? {
        if (pointer.node.elementType == calcTypes.NUMBER) {
            return {
                it.eSet(ePACKAGE.numberLiteral_Value, BigDecimal(pointer.text))
            }
        }
        return null
    }

    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        return null
    }

    override fun createObject(): EObject {
        return eFACTORY.create(ePACKAGE.numberLiteral)
    }
}