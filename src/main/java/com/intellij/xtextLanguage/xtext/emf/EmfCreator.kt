package com.intellij.xtextLanguage.xtext.emf

import com.intellij.openapi.project.Project
import com.intellij.openapi.util.Key
import com.intellij.psi.PsiElement
import com.intellij.psi.PsiFile
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import com.intellij.psi.util.CachedValue
import com.intellij.psi.util.CachedValueProvider
import com.intellij.psi.util.CachedValuesManager
import com.intellij.psi.util.PsiModificationTracker
import com.intellij.xtextLanguage.xtext.psi.SuffixElement
import org.eclipse.emf.ecore.EObject

abstract class EmfCreator {
    companion object {
        val KEY: Key<CachedValue<BridgeResult>> = Key.create("Associated emf object")
    }

    protected val bridgeMap = mutableMapOf<PsiElement, Any>()
    protected val modelDescriptions = mutableListOf<ObjectDescription>()

    protected abstract fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule?

    protected abstract fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>)

    protected abstract fun completeRawModel()

    protected abstract fun isCrossReference(psiElement: PsiElement): Boolean

    protected abstract fun createCrossReference(psiElement: PsiElement, context: EObject)

    abstract fun createBridge(psiFile: PsiFile): BridgeResult?

    fun createModel(psiElement: PsiElement): EObject? {
        val emfRoot = visitElement(psiElement)
        completeRawModel()
        return emfRoot
    }

//    fun createBridge(psiFile: PsiFile): BridgeResult?{
//        val root = createModel(psiFile)
//        root?.let {
//            return BridgeResult(root, bridgeMap)
//        }
//        return null
//    }

    protected fun getAllChildren(psiElement: PsiElement): List<PsiElement> {
        var temp: PsiElement? = psiElement.firstChild
        val result = mutableListOf<PsiElement>()
        while (temp != null) {
            if (temp !is PsiWhiteSpaceImpl) result.add(temp)
            temp = temp.nextSibling
        }
        return result
    }

    protected fun visitElement(element: PsiElement, p_current: EObject? = null): EObject? {
        val utilRule = getBridgeRuleForPsiElement(element) ?: return null
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
                val feature = literalAssignment.assign(current!!, psiElement)
                bridgeMap.put(psiElement, current!!.eGet(feature))
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
        if (current != null) {
            bridgeMap.put(element, current!!)
        }
        return current
    }

    protected fun getCached(obj: EObject, project: Project): CachedValue<EObject> {
        val manager = CachedValuesManager.getManager(project)
        val provider: CachedValueProvider<EObject> = CachedValueProvider<EObject> {

            CachedValueProvider.Result.create(obj, PsiModificationTracker.MODIFICATION_COUNT)
        }
        return manager.createCachedValue(provider)
    }


}