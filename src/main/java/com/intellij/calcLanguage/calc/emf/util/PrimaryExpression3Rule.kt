//package com.intellij.calcLanguage.calc.emf.util
//
//import arithmetics.Expression
//import com.intellij.calcLanguage.calc.psi.CalcTypes
//import com.intellij.psi.PsiElement
//import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
//import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
//import com.intellij.xtextLanguage.xtext.emf.Rewrite
//import org.eclipse.emf.common.util.EList
//import org.eclipse.emf.ecore.EObject
//
//class PrimaryExpression3Rule : CalcEmfBridgeRule() {
//    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
//        return null
//    }
//
//    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
//        if (pointer.node.elementType == CalcTypes.EXPRESSION) {
//            return object : ObjectAssignment {
//                override fun assign(obj: EObject, toAssign: EObject) {
//                    val list = obj.eGet(ePACKAGE.functionCall_Args) as EList<Expression>
//                    list.add(toAssign as Expression)
//                }
//
//            }
//        }
//        return null
//    }
//
//    override fun findRewrite(pointer: PsiElement): Rewrite? {
//        return null
//    }
//
//    override fun createObject(): EObject {
//        return eFACTORY.create(ePACKAGE.functionCall)
//    }
//}