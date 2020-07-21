package com.intellij.calcLanguage.calc.emf.util

import arithmetics.Expression
import com.intellij.calcLanguage.calc.psi.calcTypes
import com.intellij.psi.PsiElement
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

class PrimaryExpression3Rule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): ((EObject) -> Unit)? {
        return null
    }

    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        if (pointer.node.elementType == calcTypes.EXPRESSION) {
            return { current: EObject, toAssign: EObject ->
                val list = current.eGet(ePACKAGE.functionCall_Args) as EList<Expression>
                list.add(toAssign as Expression)
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        return null
    }

    override fun createMyEmfObject(): EObject {
        return eFACTORY.create(ePACKAGE.functionCall)
    }
}