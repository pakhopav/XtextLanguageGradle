package com.intellij.xtext.language.bridge.rules

import com.intellij.psi.PsiElement
import com.intellij.xtext.bridge.EmfBridgeRule
import com.intellij.xtext.bridge.LiteralAssignment
import com.intellij.xtext.bridge.ObjectAssignment
import com.intellij.xtext.bridge.Rewrite
import com.intellij.xtext.language.psi.XtextTypes
import org.eclipse.emf.ecore.EObject

class XtextWildcardBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }

    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }

    override fun createObject(): EObject {
        return org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.wildcard)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        if (pointer.node.elementType == XtextTypes.DOT_KEYWORD) {
            return org.xtext.example.xtext.xtext.XtextFactory.eINSTANCE.create(org.xtext.example.xtext.xtext.XtextPackage.eINSTANCE.wildcard)
        }
        return null
    }

}