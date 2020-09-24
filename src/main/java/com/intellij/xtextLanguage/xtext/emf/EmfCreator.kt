package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import com.intellij.xtextLanguage.xtext.psi.SuffixElement
import org.eclipse.emf.ecore.EObject

abstract class EmfCreator {

    private var emfRoot: EObject? = null
    protected val modelDescriptions = mutableListOf<ObjectDescription>()

    protected abstract fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule?

    protected abstract fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>)

    protected abstract fun completeRawModel()

    protected abstract fun isCrossReference(psiElement: PsiElement): Boolean

    protected abstract fun createCrossReference(psiElement: PsiElement, context: EObject)

    fun createModel(psiElement: PsiElement): EObject? {
        emfRoot = visitElement(psiElement)
        completeRawModel()
        return emfRoot
    }

    protected fun getAllChildren(psiElement: PsiElement): List<PsiElement> {
        var temp: PsiElement? = psiElement.firstChild
        val result = mutableListOf<PsiElement>()
        while (temp != null) {
            if (temp !is PsiWhiteSpaceImpl) result.add(temp)
            temp = temp.nextSibling
        }
        return result
    }

    protected fun visitElement(element: PsiElement, p_current: EObject? = null, p_utilRule: EmfBridgeRule? = null): EObject? {
        val utilRule = p_utilRule ?: getBridgeRuleForPsiElement(element) ?: return null
        var current: EObject? = p_current
        getAllChildren(element).forEach { psiElement ->
            if (current == null) {
                val newObject = utilRule.findAction(psiElement)
                newObject?.let {
                    current = it
                }
            }
            val rewrite = utilRule.findRewrite(psiElement)
            rewrite?.let {
                if (current == null) current = utilRule.createObject()
                current = it.rewrite(current!!)
                if (psiElement is SuffixElement) {
                    current = visitElement(psiElement, current)
                    return@forEach
                }
            }
            val literalAssignment = utilRule.findLiteralAssignment(psiElement)
            if (literalAssignment != null) {
                if (current == null) current = utilRule.createObject()
                literalAssignment.assign(current!!, psiElement)
            } else {
                val newObject = visitElement(psiElement)
                if (newObject != null) {
                    val assigment = utilRule.findObjectAssignment(psiElement)
                    if (assigment != null) {
                        if (current == null) current = utilRule.createObject()
                        assigment.assign(current!!, newObject)
                    } else {
                        current = newObject
                    }
                } else if (isCrossReference(psiElement)) {
                    if (current == null) current = utilRule.createObject()
                    createCrossReference(psiElement, current!!)
                }
            }
        }
        registerObject(current, modelDescriptions)
        return current
    }
}