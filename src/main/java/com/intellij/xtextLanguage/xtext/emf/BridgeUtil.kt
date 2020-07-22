package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject


interface BridgeUtil {

    fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule

    fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>)

}