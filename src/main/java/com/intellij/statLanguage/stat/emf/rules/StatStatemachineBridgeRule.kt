package com.intellij.statLanguage.stat.emf

import com.intellij.psi.PsiElement
import com.intellij.statLanguage.stat.psi.StatTypes
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import statemachine.Command
import statemachine.Event
import statemachine.State

class StatStatemachineBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer.node.elementType == StatTypes.EVENT) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "events" }
                    val list = obj.eGet(feature) as EList<Event>
                    list.add(toAssign as Event)
                }
            }
        } else if (pointer.node.elementType == StatTypes.COMMAND) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "commands" }
                    val list = obj.eGet(feature) as EList<Command>
                    list.add(toAssign as Command)
                }
            }
        } else if (pointer.node.elementType == StatTypes.STATE) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "states" }
                    val list = obj.eGet(feature) as EList<State>
                    list.add(toAssign as State)
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }

    override fun createObject(): EObject {
        return statemachine.StatemachineFactory.eINSTANCE.create(statemachine.StatemachinePackage.eINSTANCE.statemachine)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}