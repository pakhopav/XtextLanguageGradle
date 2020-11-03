package com.intellij.smalljavaLanguage.smalljava.emf
import com.intellij.psi.PsiElement
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJExpression
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes
import com.intellij.xtextLanguage.xtext.emf.*
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject

class SmalljavaSJSelectionExpressionBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == SmalljavaTypes.L_BRACKET_KEYWORD) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "methodinvocation" }
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EString") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, true)
                }
            }
        }
        return null
    }
    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer is SmalljavaSJExpression) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "args" }
                    feature?.let {
                        Helper.esetMany(obj, it, toAssign)
                    }
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        if (pointer.node.elementType == SmalljavaTypes.DOT_KEYWORD) {
            return object : Rewrite {
                override fun rewrite(obj: EObject): EObject {
                    val temp = smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjMemberSelection)
                    val feature = temp.eClass().eAllStructuralFeatures.firstOrNull { it.name == "receiver" }
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