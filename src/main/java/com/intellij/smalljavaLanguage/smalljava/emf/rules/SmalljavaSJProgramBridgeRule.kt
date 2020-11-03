package com.intellij.smalljavaLanguage.smalljava.emf

import com.intellij.psi.PsiElement
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJClass
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJImport
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes
import com.intellij.xtextLanguage.xtext.emf.*
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject

class SmalljavaSJProgramBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == SmalljavaTypes.QUALIFIED_NAME) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EString") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, value)
                }
            }
        }
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer is SmalljavaSJImport) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "imports" }
                    feature?.let {
                        Helper.esetMany(obj, it, toAssign)
                    }
                }
            }
        } else if (pointer is SmalljavaSJClass) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "classes" }
                    feature?.let {
                        Helper.esetMany(obj, it, toAssign)
                    }
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }

    override fun createObject(): EObject {
        return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjProgram)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}