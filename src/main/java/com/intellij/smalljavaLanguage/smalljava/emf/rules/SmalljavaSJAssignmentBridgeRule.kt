package com.intellij.smalljavaLanguage.smalljava.emf

import com.intellij.psi.PsiElement
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJExpression
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.ecore.EObject

class SmalljavaSJAssignmentBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer is SmalljavaSJExpression) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "right" }
                    obj.eSet(feature, toAssign)
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        if (pointer.node.elementType == SmalljavaTypes.EQUALS_KEYWORD) {
            return object : Rewrite {
                override fun rewrite(obj: EObject): EObject {
                    val temp = smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjAssignment)
                    val feature = temp.eClass().eAllStructuralFeatures.firstOrNull { it.name == "left" }
                    temp.eSet(feature, obj)
                    return temp
                }
            }
        }
        return null
    }

    override fun createObject(): EObject {
        return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjExpression)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}