package com.intellij.calcLanguage.calc.emf
import arithmetics.DeclaredParameter
import com.intellij.calcLanguage.calc.psi.CalcTypes
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

class CalcDefinitionBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == CalcTypes.ID) {
            return object : LiteralAssignment {
                override fun assign(obj: EObject, literal: PsiElement) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" }
                    obj.eSet(feature, literal.text)
                }
            }
        }
        return null
    }
    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer.node.elementType == CalcTypes.DECLARED_PARAMETER) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "args" }
                    val list = obj.eGet(feature) as EList<DeclaredParameter>
                    list.add(toAssign as DeclaredParameter)
                }
            }
        } else if (pointer.node.elementType == CalcTypes.DECLARED_PARAMETER) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "args" }
                    val list = obj.eGet(feature) as EList<DeclaredParameter>
                    list.add(toAssign as DeclaredParameter)
                }
            }
        } else if (pointer.node.elementType == CalcTypes.EXPRESSION) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "expr" }
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
        return arithmetics.ArithmeticsFactory.eINSTANCE.create(arithmetics.ArithmeticsPackage.eINSTANCE.definition)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}