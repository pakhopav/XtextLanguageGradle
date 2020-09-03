package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
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

    protected fun visitElement(element: PsiElement): EObject? {
        val utilRule = getBridgeRuleForPsiElement(element)
        if (utilRule == null) return null
        var current: EObject? = null
        getAllChildren(element).forEach {
            if (current == null) {
                val newObject = utilRule.findAction(it)
                newObject?.let {
//                    current = eFACTORY.create(it)
                    current = it
                }
            }
            val rewrite = utilRule.findRewrite(it)
            rewrite?.let {
                if (current == null) current = utilRule.createObject()
                current = it.rewrite(current!!)
            }
            val literalAssignment = utilRule.findLiteralAssignment(it)
            if (literalAssignment != null) {
                if (current == null) current = utilRule.createObject()
                literalAssignment.assign(current!!, it)
            } else {
                val newObject = visitElement(it)
                if (newObject != null) {
                    val assigment = utilRule.findObjectAssignment(it)
                    if (assigment != null) {
                        if (current == null) current = utilRule.createObject()
                        assigment.assign(current!!, newObject)
                    } else
                        current = newObject
                } else if (isCrossReference(it)) {
                    if (current == null) current = utilRule.createObject()
                    createCrossReference(it, current!!)
                }
            }
        }
        registerObject(current, modelDescriptions)
        return current
    }
}