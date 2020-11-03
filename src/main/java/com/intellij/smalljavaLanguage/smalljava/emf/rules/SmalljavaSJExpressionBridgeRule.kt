package com.intellij.smalljavaLanguage.smalljava.emf
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
import com.intellij.xtextLanguage.xtext.emf.Rewrite
import org.eclipse.emf.ecore.EObject

class SmalljavaSJExpressionBridgeRule : EmfBridgeRule {
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
        return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjExpression)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}