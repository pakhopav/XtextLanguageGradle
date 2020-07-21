package com.intellij.calcLanguage.calc.emf.util

import arithmetics.DeclaredParameter
import com.intellij.calcLanguage.calc.psi.calcTypes
import com.intellij.psi.PsiElement
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

class DefinitionRule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): ((EObject) -> Unit)? {
        if (pointer.node.elementType == calcTypes.ID) {
            return {
                it.eSet(ePACKAGE.abstractDefinition_Name, pointer.text)
            }
        }
        return null
    }

    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        if (pointer.node.elementType == calcTypes.DECLARED_PARAMETER) {
            return { current: EObject, toAssign: EObject ->
                val list = current.eGet(ePACKAGE.definition_Args) as EList<DeclaredParameter>
                list.add(toAssign as DeclaredParameter)
            }
        } else if (pointer.node.elementType == calcTypes.EXPRESSION) {
            return { current: EObject, toAssign: EObject ->
                current.eSet(ePACKAGE.definition_Expr, toAssign)
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        return null
    }

    override fun createMyEmfObject(): EObject {
        return eFACTORY.create(ePACKAGE.definition)
    }
}