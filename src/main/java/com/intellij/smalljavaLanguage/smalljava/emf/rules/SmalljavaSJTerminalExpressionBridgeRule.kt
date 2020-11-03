package com.intellij.smalljavaLanguage.smalljava.emf

import com.intellij.psi.PsiElement
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.ecore.EDataType
import org.eclipse.emf.ecore.EObject

class SmalljavaSJTerminalExpressionBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == SmalljavaTypes.STRING) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "value" }
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EString") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, value)
                }
            }
        } else if (pointer.node.elementType == SmalljavaTypes.INT) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "value" }
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EInt") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, value)
                }
            }
        } else if (pointer.node.elementType == SmalljavaTypes.TRUE_KEYWORD) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "value" }
                    val ePackage = org.eclipse.emf.ecore.EcorePackage.eINSTANCE
                    val classifier = ePackage.getEClassifier("EString") as EDataType
                    val value = ePackage.eFactoryInstance.createFromString(classifier, literal.text)

                    obj.eSet(feature, value)
                }
            }
        } else if (pointer.node.elementType == SmalljavaTypes.FALSE_KEYWORD) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "value" }
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
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }

    override fun createObject(): EObject {
        return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjExpression)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        if (pointer.node.elementType == SmalljavaTypes.STRING) {
            return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjStringConstant)
        } else if (pointer.node.elementType == SmalljavaTypes.INT) {
            return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjIntConstant)
        } else if (pointer.node.elementType == SmalljavaTypes.TRUE_KEYWORD) {
            return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjBoolConstant)
        } else if (pointer.node.elementType == SmalljavaTypes.FALSE_KEYWORD) {
            return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjBoolConstant)
        } else if (pointer.node.elementType == SmalljavaTypes.THIS_KEYWORD) {
            return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjThis)
        } else if (pointer.node.elementType == SmalljavaTypes.SUPER_KEYWORD) {
            return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjSuper)
        } else if (pointer.node.elementType == SmalljavaTypes.NULL_KEYWORD) {
            return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjNull)
        } else if (pointer.node.elementType == SmalljavaTypes.REFERENCE_SJ_SYMBOL_ID) {
            return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjSymbolRef)
        } else if (pointer.node.elementType == SmalljavaTypes.NEW_KEYWORD) {
            return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjNew)
        }
        return null
    }

}