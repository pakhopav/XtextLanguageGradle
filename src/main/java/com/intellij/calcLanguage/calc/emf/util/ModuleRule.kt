//package com.intellij.calcLanguage.calc.emf.util
//
//import arithmetics.Import
//import arithmetics.Statement
//import com.intellij.calcLanguage.calc.psi.CalcTypes
//import com.intellij.psi.PsiElement
//import com.intellij.xtextLanguage.xtext.emf.LiteralAssignment
//import com.intellij.xtextLanguage.xtext.emf.ObjectAssignment
//import com.intellij.xtextLanguage.xtext.emf.Rewrite
//import com.intellij.xtextLanguage.xtext.emf.StringLiteralAssignment
//import org.eclipse.emf.common.util.EList
//import org.eclipse.emf.ecore.EObject
//
//class ModuleRule : CalcEmfBridgeRule() {
//    override fun findLiteralAssignment(pointer: PsiElement): LiteralAssignment? {
//        if (pointer.node.elementType == CalcTypes.ID) {
//            return StringLiteralAssignment(ePACKAGE.module_Name)
//        }
//        return null
//    }
//
//    override fun findObjectAssignment(pointer: PsiElement): ObjectAssignment? {
//        if (pointer.node.elementType == CalcTypes.IMPORT) {
//            return object : ObjectAssignment {
//                override fun assign(obj: EObject, toAssign: EObject) {
//                    val list = obj.eGet(ePACKAGE.module_Imports) as EList<Import>
//                    list.add(toAssign as Import)
//                }
//
//            }
//        } else if (pointer.node.elementType == CalcTypes.STATEMENT) {
//            return object : ObjectAssignment {
//                override fun assign(obj: EObject, toAssign: EObject) {
//                    val list = obj.eGet(ePACKAGE.module_Statements) as EList<Statement>
//                    list.add(toAssign as Statement)
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
//        return eFACTORY.create(ePACKAGE.module)
//    }
//}