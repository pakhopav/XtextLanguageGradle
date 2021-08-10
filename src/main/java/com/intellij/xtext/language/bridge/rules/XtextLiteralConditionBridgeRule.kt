package com.intellij.xtext.language.bridge.rules

import com.intellij.psi.PsiElement
import com.intellij.xtext.bridge.EmfBridgeRule
import com.intellij.xtext.bridge.LiteralAssignment
import com.intellij.xtext.bridge.ObjectAssignment
import com.intellij.xtext.bridge.Rewrite
import com.intellij.xtext.language.psi.XtextTypes
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import kotlin.test.assertNotNull

class XtextLiteralConditionBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == XtextTypes.TRUE_KEYWORD) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement): EStructuralFeature {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "true" }
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
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }

    override fun createObject(): EObject {
        return org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.literalCondition)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}