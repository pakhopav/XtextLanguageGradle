package com.intellij.calcLanguage.calc.emf

import arithmetics.Expression
import com.intellij.calcLanguage.calc.psi.CalcTypes
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

class CalcPrimaryExpression3BridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer.node.elementType == CalcTypes.EXPRESSION) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "args" }
                    val list = obj.eGet(feature) as EList<Expression>
                    list.add(toAssign as Expression)
                }
            }
        } else if (pointer.node.elementType == CalcTypes.EXPRESSION) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "args" }
                    val list = obj.eGet(feature) as EList<Expression>
                    list.add(toAssign as Expression)
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }

    override fun createObject(): EObject {
        return arithmetics.ArithmeticsFactory.eINSTANCE.create(arithmetics.ArithmeticsPackage.eINSTANCE.expression)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        if (pointer.node.elementType == CalcTypes.REFERENCE_ABSTRACT_DEFINITION_ID) {
            return arithmetics.ArithmeticsFactory.eINSTANCE.create(arithmetics.ArithmeticsPackage.eINSTANCE.functionCall)
        }
        return null
    }

}