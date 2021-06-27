package com.intellij.xtextLanguage.xtext.emf.rules

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.bridge.*
import com.intellij.xtextLanguage.xtext.psi.XtextTypes
import org.eclipse.emf.ecore.EObject

class XtextTerminalAlternativesBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        if (pointer.node.elementType == XtextTypes.TERMINAL_ALTERNATIVES_SUFFIX_1) {
            return object : Rewrite {
                override fun rewrite(obj: EObject): EObject {
                    val temp =
                        org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.alternatives)
                    val feature = temp.eClass().eAllStructuralFeatures.firstOrNull { it.name == "elements" }
                    feature?.let {
                        Helper.esetMany(temp, it, obj)
                    }
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