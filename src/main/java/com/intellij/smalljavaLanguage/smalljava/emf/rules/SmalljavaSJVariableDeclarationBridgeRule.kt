package com.intellij.smalljavaLanguage.smalljava.emf
import com.intellij.psi.PsiElement
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJExpression
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject

class SmalljavaSJVariableDeclarationBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == SmalljavaTypes.ID) {
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
        if (pointer is SmalljavaSJExpression) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "expression" }
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
        return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjVariableDeclaration)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}