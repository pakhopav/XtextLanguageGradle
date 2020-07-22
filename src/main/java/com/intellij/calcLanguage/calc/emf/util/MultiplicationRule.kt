package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.CalcMultiplication
import com.intellij.calcLanguage.calc.psi.CalcPrimaryExpression
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.ecore.EObject

class MultiplicationRule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        val context = pointer.context as CalcMultiplication
        if (pointer is CalcPrimaryExpression) {
            if (context.primaryExpressionList.first() != pointer) {
                return object : ObjectAssignment {
                    override fun assign(obj: EObject, toAssign: EObject) {
                        if (ePACKAGE.multi.isInstance(obj)) {
                            obj.eSet(ePACKAGE.multi_Right, toAssign)
                        } else {
                            obj.eSet(ePACKAGE.div_Right, toAssign)
                        }
                    }

                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        if (pointer.text == "*") {
            return object : Rewrite {
                override fun rewrite(obj: EObject?): EObject {
                    val temp = eFACTORY.create(ePACKAGE.multi)
                    temp.eSet(ePACKAGE.multi_Left, obj)
                    return temp
                }

            }
        } else if (pointer.text == "/") {
            return object : Rewrite {
                override fun rewrite(obj: EObject?): EObject {
                    val temp = eFACTORY.create(ePACKAGE.div)
                    temp.eSet(ePACKAGE.div_Left, obj)
                    return temp
                }

            }
        }
        return null
    }

    override fun createObject(): EObject {
        return eFACTORY.create(ePACKAGE.expression)
    }
}