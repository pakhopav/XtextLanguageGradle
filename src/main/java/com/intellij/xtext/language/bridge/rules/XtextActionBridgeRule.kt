package com.intellij.xtext.language.bridge.rules

import com.intellij.psi.PsiElement
import com.intellij.xtext.bridge.EmfBridgeRule
import com.intellij.xtext.bridge.LiteralAssignment
import com.intellij.xtext.bridge.ObjectAssignment
import com.intellij.xtext.bridge.Rewrite
import com.intellij.xtext.language.psi.XtextTypeRef
import com.intellij.xtext.language.psi.XtextTypes
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import kotlin.test.assertNotNull

class XtextActionBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == XtextTypes.VALID_ID) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement): EStructuralFeature {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "feature" }
                    assertNotNull(feature)
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EString") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, value)
                    return feature
                }
            }
        } else if (pointer.node.elementType == XtextTypes.EQUALS_KEYWORD) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement): EStructuralFeature {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "operator" }
                    assertNotNull(feature)
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EString") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, value)
                    return feature
                }
            }
        } else if (pointer.node.elementType == XtextTypes.PLUS_EQUALS_KEYWORD) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement): EStructuralFeature {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "operator" }
                    assertNotNull(feature)
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EString") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, value)
                    return feature
                }
            }
        }
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer is XtextTypeRef) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "type" }
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
        return org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.action)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}