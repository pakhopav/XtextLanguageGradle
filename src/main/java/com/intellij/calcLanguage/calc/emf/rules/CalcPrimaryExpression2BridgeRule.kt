package com.intellij.calcLanguage.calc.emf
import com.intellij.calcLanguage.calc.psi.CalcTypes
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.ecore.EObject
import java.math.BigDecimal

class CalcPrimaryExpression2BridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == CalcTypes.NUMBER) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "value" }
                    obj.eSet(feature, BigDecimal(literal.text))
                }
            }
        }
        return null
    }
    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }
    override fun createObject(): EObject {
        return arithmetics.ArithmeticsFactory.eINSTANCE.create(arithmetics.ArithmeticsPackage.eINSTANCE.numberLiteral)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}