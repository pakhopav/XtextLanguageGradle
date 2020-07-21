package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.calcTypes
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.ecore.EObject
import java.math.BigDecimal

class PrimaryExpression2Rule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == calcTypes.NUMBER) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject) {
                    obj.eSet(ePACKAGE.numberLiteral_Value, BigDecimal(pointer.text))
                }

                override fun isName(): Boolean {
                    return false
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
        return eFACTORY.create(ePACKAGE.numberLiteral)
    }
}