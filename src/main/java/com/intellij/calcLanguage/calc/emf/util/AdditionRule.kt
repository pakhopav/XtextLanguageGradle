package com.intellij.calcLanguage.calc.emf.util

import com.intellij.calcLanguage.calc.psi.CalcAddition
import com.intellij.calcLanguage.calc.psi.CalcMultiplication
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.ecore.EObject

class AdditionRule : CalcEmfBridgeRule() {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        val context = pointer.context as CalcAddition
        if (pointer is CalcMultiplication) {
            if (context.multiplicationList.first() != pointer) {
                return object : ObjectAssignment {
                    override fun assign(obj: EObject, toAssign: EObject) {
                        if (ePACKAGE.plus.isInstance(obj)) {
                            obj.eSet(ePACKAGE.plus_Right, toAssign)
                        } else {
                            obj.eSet(ePACKAGE.minus_Right, toAssign)
                        }
                    }

                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        if (pointer.text == "+") {
            return object : Rewrite {
                override fun rewrite(obj: EObject?): EObject {
                    val temp = eFACTORY.create(ePACKAGE.plus)
                    temp.eSet(ePACKAGE.plus_Left, obj)
                    return temp
                }
            }
        } else if (pointer.text == "-") {
            return object : Rewrite {
                override fun rewrite(obj: EObject?): EObject {
                    val temp = eFACTORY.create(ePACKAGE.minus)
                    temp.eSet(ePACKAGE.minus_Left, obj)
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