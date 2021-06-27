package com.intellij.xtextLanguage.xtext.emf.rules

import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.bridge.*
import com.intellij.xtextLanguage.xtext.psi.XtextAnnotation
import com.intellij.xtextLanguage.xtext.psi.XtextTerminalAlternatives
import com.intellij.xtextLanguage.xtext.psi.XtextTypeRef
import com.intellij.xtextLanguage.xtext.psi.XtextTypes
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import kotlin.test.assertNotNull

class XtextTerminalRuleBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == XtextTypes.FRAGMENT_KEYWORD) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement): EStructuralFeature {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "fragment" }
                    assertNotNull(feature)
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EString") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, true)
                    return feature
                }
            }
        } else if (pointer.node.elementType == XtextTypes.VALID_ID) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement): EStructuralFeature {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
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
        if (pointer is XtextAnnotation) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "annotations" }
                    feature?.let {
                        Helper.esetMany(obj, it, toAssign)
                    }
                }
            }
        } else if (pointer is XtextTypeRef) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "type" }
                    obj.eSet(feature, toAssign)
                }
            }
        } else if (pointer is XtextTerminalAlternatives) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "alternatives" }
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
        return org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.terminalRule)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}