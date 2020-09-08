package com.intellij.statLanguage.stat.emf
import com.intellij.psi.PsiElement
import com.intellij.statLanguage.stat.emf.scope.StatScope
import com.intellij.statLanguage.stat.psi.*
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.EmfCreator
import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
import org.eclipse.emf.common.util.EList
import org.eclipse.emf.ecore.EObject
import statemachine.*

class StatEmfCreator : EmfCreator() {
    private val STATEMACHINE = StatStatemachineBridgeRule()
    private val EVENT = StatEventBridgeRule()
    private val COMMAND = StatCommandBridgeRule()
    private val STATE = StatStateBridgeRule()
    private val TRANSITION = StatTransitionBridgeRule()
    private val statemachineToEventNameList = mutableListOf<Pair<Statemachine, String>>()
    private val stateToCommandNameList = mutableListOf<Pair<State, String>>()
    private val transitionToEventNameList = mutableListOf<Pair<Transition, String>>()
    private val transitionToStateNameList = mutableListOf<Pair<Transition, String>>()
    override fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule? {
        if (psiElement is StatStatemachine) {
            return STATEMACHINE
        }
        if (psiElement is StatEvent) {
            return EVENT
        }
        if (psiElement is StatCommand) {
            return COMMAND
        }
        if (psiElement is StatState) {
            return STATE
        }
        if (psiElement is StatTransition) {
            return TRANSITION
        } 
        return null
    }
    override fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>) {
        obj?.let {
            if (obj is Event) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" } 
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is Command) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" } 
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is State) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" } 
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else return
        }
    }
    override fun completeRawModel() {
        val scope = StatScope(modelDescriptions)
        statemachineToEventNameList.forEach {
            val container = it.first
            val resolvedDefinition = scope.getSingleElement(it.second)?.obj
            val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "resetEvents" }
            resolvedDefinition?.let {
                val list = container.eGet(feature) as EList<Event>
                list.add(it as Event)
            }
        }
        stateToCommandNameList.forEach {
            val container = it.first
            val resolvedDefinition = scope.getSingleElement(it.second)?.obj
            val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "actions" }
            resolvedDefinition?.let {
                val list = container.eGet(feature) as EList<Command>
                list.add(it as Command)
            }
        }
        transitionToEventNameList.forEach {
            val container = it.first
            val resolvedDefinition = scope.getSingleElement(it.second)?.obj
            val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "event" }
            resolvedDefinition?.let {
                container.eSet(feature, it)
            }
        }
        transitionToStateNameList.forEach {
            val container = it.first
            val resolvedDefinition = scope.getSingleElement(it.second)?.obj
            val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "state" }
            resolvedDefinition?.let {
                container.eSet(feature, it)
            }
        }
    }
    override fun isCrossReference(psiElement: PsiElement): Boolean {
        return psiElement is StatREFERENCEEventID || psiElement is StatREFERENCECommandID || psiElement is StatREFERENCEEventID || psiElement is StatREFERENCEStateID
    }
    override fun createCrossReference(psiElement: PsiElement, container: EObject) {
        if (container is Statemachine && psiElement is StatREFERENCEEventID)
            statemachineToEventNameList.add(Pair(container, psiElement.text))
        else if (container is State && psiElement is StatREFERENCECommandID)
            stateToCommandNameList.add(Pair(container, psiElement.text))
        else if (container is Transition && psiElement is StatREFERENCEEventID)
            transitionToEventNameList.add(Pair(container, psiElement.text))
        else if (container is Transition && psiElement is StatREFERENCEStateID)
            transitionToStateNameList.add(Pair(container, psiElement.text))
    }
}