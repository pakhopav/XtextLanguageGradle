package com.intellij.calcLanguage.calc.emf.util

import arithmetics.DeclaredParameter
import com.intellij.calcLanguage.calc.psi.calcTypes
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import com.intellij.xtextLanguage.xtext.emf.StringLiteralAssignment
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

class DefinitionRule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == calcTypes.ID) {
            return StringLiteralAssignment(ePACKAGE.abstractDefinition_Name)
        }
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer.node.elementType == calcTypes.DECLARED_PARAMETER) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val list = obj.eGet(ePACKAGE.definition_Args) as EList<DeclaredParameter>
                    list.add(toAssign as DeclaredParameter)
                }

            }
        } else if (pointer.node.elementType == calcTypes.EXPRESSION) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    obj.eSet(ePACKAGE.definition_Expr, toAssign)
                }

            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }

    override fun createObject(): EObject {
        return eFACTORY.create(ePACKAGE.definition)
    }
}