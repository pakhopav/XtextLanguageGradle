package com.intellij.xtextLanguage.xtext.emf.rules

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.bridge.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.bridge.LiteralAssignment
import com.intellij.xtextLanguage.xtext.bridge.ObjectAssignment
import com.intellij.xtextLanguage.xtext.bridge.Rewrite
import com.intellij.xtextLanguage.xtext.psi.XtextConjunction1
import com.intellij.xtextLanguage.xtext.psi.XtextTypes
import org.eclipse.emf.ecore.EObject

class XtextDisjunctionBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer is XtextConjunction1) {
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
        if (pointer.node.elementType == XtextTypes.PIPE_KEYWORD) {
            return object : Rewrite {
                override fun rewrite(obj: EObject): EObject {
                    val temp =
                        org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.disjunction)
                    val feature = temp.eClass().eAllStructuralFeatures.firstOrNull { it.name == "left" }
                    temp.eSet(feature, obj)
                    return temp
                }
            }
        }
        return null
    }

    override fun createObject(): EObject {
        return org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.condition)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}