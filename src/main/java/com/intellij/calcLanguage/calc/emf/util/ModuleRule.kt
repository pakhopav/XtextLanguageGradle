package com.intellij.calcLanguage.calc.emf.util

import arithmetics.Import
import arithmetics.Statement
import com.intellij.calcLanguage.calc.psi.calcModule
import com.intellij.calcLanguage.calc.psi.calcTypes
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

class ModuleRule : EmfBridgeRule {

    override fun findAssignment(pointer: PsiElement): ((EObject, EObject) -> Unit)? {
        val context = pointer.context as calcModule
        if (pointer.node.elementType == calcTypes.ID) {
            return { current: EObject, toAssign: EObject ->
                current.eSet(CalcEmfBridgeUtil.ePACKAGE.module_Name, toAssign)
            }
        } else if (pointer.node.elementType == calcTypes.IMPORT) {
            return { current: EObject, toAssign: EObject ->
                val list = current.eGet(CalcEmfBridgeUtil.ePACKAGE.module_Imports) as EList<Import>
                list.add(toAssign as Import)
            }
        } else if (pointer.node.elementType == calcTypes.STATEMENT) {
            return { current: EObject, toAssign: EObject ->
                val list = current.eGet(CalcEmfBridgeUtil.ePACKAGE.module_Statements) as EList<Statement>
                list.add(toAssign as Statement)
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): ((EObject?) -> EObject)? {
        return null
    }
}