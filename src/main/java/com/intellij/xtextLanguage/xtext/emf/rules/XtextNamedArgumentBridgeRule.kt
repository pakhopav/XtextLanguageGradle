package com.intellij.xtextLanguage.xtext.emf.rules

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.bridge.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.bridge.LiteralAssignment
import com.intellij.xtextLanguage.xtext.bridge.ObjectAssignment
import com.intellij.xtextLanguage.xtext.bridge.Rewrite
import com.intellij.xtextLanguage.xtext.psi.XtextDisjunction
import com.intellij.xtextLanguage.xtext.psi.XtextTypes
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import kotlin.test.assertNotNull

class XtextNamedArgumentBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == XtextTypes.EQUALS_KEYWORD) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement): EStructuralFeature {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "calledByName" }
                    assertNotNull(feature)
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EString") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, true)
                    return feature
                }
            }
        }
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer is XtextDisjunction) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "value" }
                    obj.eSet(feature, toAssign)
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }

    override fun createObject(): EObject {
        return org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.namedArgument)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}