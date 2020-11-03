package com.intellij.smalljavaLanguage.smalljava.emf
import com.intellij.psi.PsiElement
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJStatement
import com.intellij.xtextLanguage.xtext.emf.*
import org.eclipse.emf.ecore.EObject

class SmalljavaSJIfBlockBridgeRule : EmfBridgeRule {
    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
        return null
    }
    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
        if (pointer is SmalljavaSJStatement) {
            return object : ObjectAssignment {
                override fun assign(obj: EObject, toAssign: EObject) {
                    val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "statements" }
                    feature?.let {
                        Helper.esetMany(obj, it, toAssign)
                    }
                }
            }
        }
        return null
    }

    override fun findRewrite(pointer: PsiElement): Rewrite? {
        return null
    }

    override fun createObject(): EObject {
        return smallJava.SmallJavaFactory.eINSTANCE.create(smallJava.SmallJavaPackage.eINSTANCE.sjIfBlock)
    }

    override fun findAction(pointer: PsiElement): EObject? {
        return null
    }

}