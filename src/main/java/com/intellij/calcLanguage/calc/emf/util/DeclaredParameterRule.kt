//package com.intellij.calcLanguage.calc.emf.util
//
//import com.intellij.calcLanguage.calc.psi.CalcTypes
//import com.intellij.psi.PsiElement
//import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
//import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
//import com.intellij.xtextLanguage.xtext.emf.Rewrite
//import com.intellij.xtextLanguage.xtext.emf.StringLiteralAssignment
//import org.eclipse.emf.ecore.EObject
//
//class DeclaredParameterRule : CalcEmfBridgeRule() {
//    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
//        if (pointer.node.elementType == CalcTypes.ID) {
//            return StringLiteralAssignment(ePACKAGE.abstractDefinition_Name)
//        }
//        return null
//    }
//
//    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
//        return null
//    }
//
//    override fun findRewrite(pointer: PsiElement): Rewrite? {
//        return null
//    }
//
//    override fun createObject(): EObject {
//        return eFACTORY.create(ePACKAGE.declaredParameter)
//    }
//
//
//}