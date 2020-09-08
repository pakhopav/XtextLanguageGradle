package com.intellij.statLanguage.stat.emf
import com.intellij.psi.PsiElement
import com.intellij.statLanguage.stat.psi.StatTypes
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import statemachine.Transition

class StatStateBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        if (pointer.node.elementType == StatTypes.ID) {
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
        if (pointer.node.elementType == StatTypes.TRANSITION) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "transitions" }
                    val list = obj.eGet(feature) as EList<Transition>
                    list.add(toAssign as Transition)
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }
    override fun createObject(): EObject {
        return statemachine.StatemachineFactory.eINSTANCE.create(statemachine.StatemachinePackage.eINSTANCE.state)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}