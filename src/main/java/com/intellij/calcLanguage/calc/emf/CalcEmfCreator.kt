//package com.intellij.calcLanguage.calc.emf
//
//
//import arithmetics.*
//import com.intellij.calcLanguage.calc.emf.util.*
//import com.intellij.calcLanguage.calc.psi.*
//import com.intellij.entityLanguage.entity.emf.scope.EntityScope
//import com.intellij.psi.PsiElement
//import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
//import com.intellij.xtextLanguage.xtext.emf.EmfCreator
//import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
//import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
//import org.eclipse.emf.ecore.EObject
//
//
//class CalcEmfCreator : EmfCreator() {
//
//    private val eFACTORY = ArithmeticsFactory.eINSTANCE
//    private val ePACKAGE = ArithmeticsPackage.eINSTANCE
//    private val MODULE = ModuleRule()
//    private val EVALUATION = EvaluationRule()
//    private val DEFINITION = DefinitionRule()
//    private val STATEMENT = StatementRule()
//    private val DECLARED_PARAMETER = DeclaredParameterRule()
//    private val EXPRESSION = ExpressionRule()
//    private val MULTIPLICATION = MultiplicationRule()
//    private val ADDITION = AdditionRule()
//    private val PRIMARY_EXPRESSION = PrimaryExpressionRule()
//    private val PRIMARY_EXPRESSION1 = PrimaryExpression1Rule()
//    private val PRIMARY_EXPRESSION2 = PrimaryExpression2Rule()
//    private val PRIMARY_EXPRESSION3 = PrimaryExpression3Rule()
//    private val IMPORT = ImportRule()
//
//
//    private var referencedAbstractDefinitions = mutableMapOf<FunctionCall, String>()
//    private var referencedModules = mutableMapOf<Import, String>()
//
//    override fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule {
//        if (psiElement is CalcModule) {
//            return MODULE
//        } else if (psiElement is CalcMultiplication) {
//            return MULTIPLICATION
//        } else if (psiElement is CalcPrimaryExpression) {
//            return PRIMARY_EXPRESSION
//        } else if (psiElement is CalcImport) {
//            return IMPORT
//        } else if (psiElement is CalcStatement) {
//            return STATEMENT
//        } else if (psiElement is CalcDeclaredParameter) {
//            return DECLARED_PARAMETER
//        } else if (psiElement is CalcExpression) {
//            return EXPRESSION
//        } else if (psiElement is CalcPrimaryExpression1) {
//            return PRIMARY_EXPRESSION1
//        } else if (psiElement is CalcPrimaryExpression2) {
//            return PRIMARY_EXPRESSION2
//        } else if (psiElement is CalcPrimaryExpression3) {
//            return PRIMARY_EXPRESSION3
//        } else if (psiElement is CalcEvaluation) {
//            return EVALUATION
//        } else if (psiElement is CalcDefinition) {
//            return DEFINITION
//        } else {
//            return ADDITION
//        }
//    }
//
//    override fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>) {
//        obj?.let {
//            if (obj is Module) {
//                descriptions.add(ObjectDescriptionImpl(it, it.eGet(ePACKAGE.module_Name) as String))
//            } else if (obj is AbstractDefinition) {
//                descriptions.add(ObjectDescriptionImpl(it, it.eGet(ePACKAGE.abstractDefinition_Name) as String))
//            } else return
//        }
//    }
//
//
//    override fun completeRawModel() {
//        val scope = EntityScope(modelDescriptions)
//        referencedAbstractDefinitions.forEach {
//            val container = it.key
//            val resolvedDefinition = scope.getSingleElement(it.value)?.obj
//            resolvedDefinition?.let { container.eSet(ArithmeticsPackage.eINSTANCE.functionCall_Func, resolvedDefinition) }
//        }
//
//        referencedModules.forEach {
//            val container = it.key
//            val resolvedModule = scope.getSingleElement(it.value)?.obj
//            resolvedModule?.let { container.eSet(ArithmeticsPackage.eINSTANCE.import_Module, resolvedModule) }
//        }
//    }
//
//
//    override fun isCrossReference(psiElement: PsiElement): Boolean {
//        return psiElement is CalcREFERENCEAbstractDefinitionID || psiElement is CalcREFERENCEModuleID
//    }
//
//    override fun createCrossReference(psiElement: PsiElement, container: EObject) {
//        if (psiElement is CalcREFERENCEModuleID)
//            referencedModules.put(container as Import, psiElement.text)
//        else if (psiElement is CalcREFERENCEAbstractDefinitionID)
//            referencedAbstractDefinitions.put(container as FunctionCall, psiElement.text)
//    }
//
//}