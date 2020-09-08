package com.intellij.calcLanguage.calc.emf
import arithmetics.Import
import arithmetics.Statement
import com.intellij.calcLanguage.calc.psi.CalcTypes
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject

class CalcModuleBridgeRule : EmfBridgeRule {
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
        if (pointer.node.elementType == CalcTypes.IMPORT) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "imports" }
                    val list = obj.eGet(feature) as EList<Import>
                    list.add(toAssign as Import)
                }
            }
        } else if (pointer.node.elementType == CalcTypes.STATEMENT) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "statements" }
                    val list = obj.eGet(feature) as EList<Statement>
                    list.add(toAssign as Statement)
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }
    override fun createObject(): EObject {
        return arithmetics.ArithmeticsFactory.eINSTANCE.create(arithmetics.ArithmeticsPackage.eINSTANCE.module)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}