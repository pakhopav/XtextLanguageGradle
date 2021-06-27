package com.intellij.xtextLanguage.xtext.emf.rules

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.bridge.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.bridge.LiteralAssignment
import com.intellij.xtextLanguage.xtext.bridge.ObjectAssignment
import com.intellij.xtextLanguage.xtext.bridge.Rewrite
import com.intellij.xtextLanguage.xtext.psi.XtextKeyword1
import com.intellij.xtextLanguage.xtext.psi.XtextTypes
import org.eclipse.emf.ecore.EObject

class XtextCharacterRangeBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer is XtextKeyword1) {
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
        if (pointer.node.elementType == XtextTypes.RANGE_KEYWORD) {
            return object : Rewrite {
                override fun rewrite(obj: EObject): EObject {
                    val temp =
                        org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.characterRange)
                    val feature = temp.eClass().eAllStructuralFeatures.firstOrNull { it.name == "left" }
                    temp.eSet(feature, obj)
                    return temp
                }
            }
        }
        return null
    }

    override fun createObject(): EObject {
        return org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.abstractElement)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}