package com.intellij.calcLanguage.calc.emf.util

import arithmetics.AbstractDefinition
import arithmetics.ArithmeticsFactory
import arithmetics.ArithmeticsPackage
import arithmetics.Module
import com.intellij.calcLanguage.calc.psi.*
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
import org.eclipse.emf.ecore.EObject

abstract class CalcEmfBridgeRule : EmfBridgeRule {
    protected val eFACTORY = ArithmeticsFactory.eINSTANCE
    protected val ePACKAGE = ArithmeticsPackage.eINSTANCE


    companion object {
        private val eFACTORY = ArithmeticsFactory.eINSTANCE
        private val ePACKAGE = ArithmeticsPackage.eINSTANCE

        private val MODULE = ModuleRule()
        private val EVALUATION = EvaluationRule()
        private val DEFINITION = DefinitionRule()
        private val STATEMENT = StatementRule()
        private val DECLARED_PARAMETER = DeclaredParameterRule()
        private val EXPRESSION = ExpressionRule()
        private val MULTIPLICATION = MultiplicationRule()
        private val ADDITION = AdditionRule()
        private val PRIMARY_EXPRESSION = PrimaryExpressionRule()
        private val PRIMARY_EXPRESSION1 = PrimaryExpression1Rule()
        private val PRIMARY_EXPRESSION2 = PrimaryExpression2Rule()
        private val PRIMARY_EXPRESSION3 = PrimaryExpression3Rule()
        private val IMPORT = ImportRule()

        fun getUtilRuleClass(psiElement: PsiElement): EmfBridgeRule {
            if (psiElement is CalcModule) {
                return MODULE
            } else if (psiElement is CalcMultiplication) {
                return MULTIPLICATION
            } else if (psiElement is CalcPrimaryExpression) {
                return PRIMARY_EXPRESSION
            } else if (psiElement is CalcImport) {
                return IMPORT
            } else if (psiElement is CalcStatement) {
                return STATEMENT
            } else if (psiElement is CalcDeclaredParameter) {
                return DECLARED_PARAMETER
            } else if (psiElement is CalcExpression) {
                return EXPRESSION
            } else if (psiElement is CalcPrimaryExpression1) {
                return PRIMARY_EXPRESSION1
            } else if (psiElement is CalcPrimaryExpression2) {
                return PRIMARY_EXPRESSION2
            } else if (psiElement is CalcPrimaryExpression3) {
                return PRIMARY_EXPRESSION3
            } else if (psiElement is CalcEvaluation) {
                return EVALUATION
            } else if (psiElement is CalcDefinition) {
                return DEFINITION
            } else {
                return ADDITION
            }
        }

        fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>) {
            obj?.let {
                if (obj is Module) {
                    descriptions.add(ObjectDescriptionImpl(it, it.eGet(ePACKAGE.module_Name) as String))
                } else if (obj is AbstractDefinition) {
                    descriptions.add(ObjectDescriptionImpl(it, it.eGet(ePACKAGE.abstractDefinition_Name) as String))
                } else {

                }
            }

        }
    }
}