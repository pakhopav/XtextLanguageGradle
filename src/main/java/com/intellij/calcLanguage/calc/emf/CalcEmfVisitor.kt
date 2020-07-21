package com.intellij.calcLanguage.calc.emf


import arithmetics.*
import com.intellij.calcLanguage.calc.emf.util.*
import com.intellij.calcLanguage.calc.psi.*
import com.intellij.entityLanguage.entity.emf.scope.EntityScope
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
import org.eclipse.emf.ecore.EObject
import java.math.BigDecimal


class CalcEmfVisitor {

    private var emfRoot: Module? = null
    private var referencedAbstractDefinitions = mutableMapOf<FunctionCall, String>()
    private var referencedModules = mutableMapOf<Import, String>()
    private var modelDescriptions = mutableListOf<ObjectDescription>()
    private val factory = ArithmeticsFactory.eINSTANCE
    private val ePackage = ArithmeticsPackage.eINSTANCE


    fun createModel(psiModule: calcModule): Module? {
        visitModule(psiModule)
        completeRawModel()
        return emfRoot
    }

    fun visitAddition(psiAddition: calcAddition): Expression {
        val utilRule = CalcEmfBridgeUtil.ADDITION
        return visitElement(psiAddition, utilRule) as Expression
    }


    fun visitDeclaredParameter(psiDeclaredParameter: calcDeclaredParameter): DeclaredParameter {
        val utilRule = DeclaredParameterRule()
        val declaredParameter = visitElement(psiDeclaredParameter, utilRule) as DeclaredParameter
        modelDescriptions.add(ObjectDescriptionImpl(declaredParameter, declaredParameter.eGet(ePackage.abstractDefinition_Name).toString()))
        return declaredParameter
    }

    fun visitDefinition(psiDefinition: calcDefinition): Definition {
        val utilRule = DefinitionRule()
        val definition = visitElement(psiDefinition, utilRule) as Definition
        modelDescriptions.add(ObjectDescriptionImpl(definition, definition.eGet(ePackage.abstractDefinition_Name).toString()))
        return definition
    }

    fun visitEvaluation(psiEvaluation: calcEvaluation): Evaluation {
        val utilRule = EvaluationRule()
        return visitElement(psiEvaluation, utilRule) as Evaluation
    }

    fun visitExpression(psiExpression: calcExpression): Expression {
        return visitAddition(psiExpression.addition)
    }

    fun visitImport(psiImport: calcImport): Import {
        val utilRule = CalcEmfBridgeUtil.IMPORT
        return visitElement(psiImport, utilRule) as Import
    }

    fun visitModule(psiModule: calcModule): Module? {
        val utilRule = CalcEmfBridgeUtil.MODULE
        emfRoot = visitElement(psiModule, utilRule) as Module
        modelDescriptions.add(ObjectDescriptionImpl(emfRoot!!, emfRoot!!.eGet(ePackage.module_Name).toString()))
        return emfRoot
    }

    fun visitMultiplication(psiMultiplication: calcMultiplication): Expression? {
        val utilRule = CalcEmfBridgeUtil.MULTIPLICATION
        return visitElement(psiMultiplication, utilRule) as Expression
    }

    fun visitPrimaryExpression(psiPrimaryExpression: calcPrimaryExpression): Expression? {
        psiPrimaryExpression.primaryExpression1?.let { return visitPrimaryExpression1(it) }
        psiPrimaryExpression.primaryExpression2?.let { return visitPrimaryExpression2(it) }
        psiPrimaryExpression.primaryExpression3?.let { return visitPrimaryExpression3(it) }
        return null
    }

    fun visitPrimaryExpression1(psiPrimaryExpression1: calcPrimaryExpression1): Expression {
        return visitExpression(psiPrimaryExpression1.expression)
    }

    fun visitPrimaryExpression2(psiPrimaryExpression2: calcPrimaryExpression2): NumberLiteral {
        val current = factory.create(ePackage.numberLiteral) as NumberLiteral
        val number = BigDecimal(psiPrimaryExpression2.number.text)
        current.eSet(ePackage.numberLiteral_Value, number)
        return current
    }

    fun visitPrimaryExpression3(psiPrimaryExpression3: calcPrimaryExpression3): FunctionCall {
        val utilRule = PrimaryExpression3Rule()
        return visitElement(psiPrimaryExpression3, utilRule) as FunctionCall
    }

    fun visitREFERENCEAbstractDefinitionID(psiAbstractDefinitionID: calcREFERENCEAbstractDefinitionID, functionCall: FunctionCall) {
        referencedAbstractDefinitions.put(functionCall, psiAbstractDefinitionID.text)
    }

    fun visitREFERENCEModuleID(psiModuleID: calcREFERENCEModuleID, import: Import) {
        referencedModules.put(import, psiModuleID.text)
    }

    fun visitStatement(psiStatement: calcStatement): Statement? {
        psiStatement.definition?.let { return visitDefinition(it) }
        psiStatement.evaluation?.let { return visitEvaluation(it) }
        return null
    }


    private fun completeRawModel() {
        val scope = EntityScope(modelDescriptions)
        referencedAbstractDefinitions.forEach {
            val container = it.key
            val resolvedDefinition = scope.getSingleElement(it.value)?.obj
            resolvedDefinition?.let { container.eSet(ePackage.functionCall_Func, resolvedDefinition) }
        }

        referencedModules.forEach {
            val container = it.key
            val resolvedModule = scope.getSingleElement(it.value)?.obj
            resolvedModule?.let { container.eSet(ePackage.import_Module, resolvedModule) }

        }
    }


    fun createEmfObjectIfPossible(psiElement: PsiElement): EObject? {
        if (psiElement is calcMultiplication) {
            return visitMultiplication(psiElement)
        } else if (psiElement is calcPrimaryExpression) {
            return visitPrimaryExpression(psiElement)
        } else if (psiElement is calcImport) {
            return visitImport(psiElement)
        } else if (psiElement is calcStatement) {
            return visitStatement(psiElement)
        } else if (psiElement is calcDeclaredParameter) {
            return visitDeclaredParameter(psiElement)
        } else if (psiElement is calcExpression) {
            return visitExpression(psiElement)
        }

        return null
    }


    fun getAllChildren(psiElement: PsiElement): List<PsiElement> {
        var temp: PsiElement? = psiElement.firstChild
        val result = mutableListOf<PsiElement>()
        while (temp != null) {
            if (temp !is PsiWhiteSpaceImpl) result.add(temp)
            temp = temp.nextSibling
        }
        return result
    }

    fun isCrossReference(psiElement: PsiElement): Boolean {
        return psiElement is calcREFERENCEAbstractDefinitionID || psiElement is calcREFERENCEModuleID
    }

    fun createCrossReference(psiElement: PsiElement, container: EObject) {
        if (psiElement is calcREFERENCEModuleID) visitREFERENCEModuleID(psiElement, container as Import)
        else if (psiElement is calcREFERENCEAbstractDefinitionID) visitREFERENCEAbstractDefinitionID(psiElement, container as FunctionCall)
    }

    fun visitElement(element: PsiElement, utilRule: EmfBridgeRule): EObject? {
        var current: EObject? = null
        getAllChildren(element).forEach {
            val rewrite = utilRule.findRewrite(it)
            if (rewrite != null) current = rewrite(current)
            val literalAssignment = utilRule.findLiteralAssignment(it)
            if (literalAssignment != null) {
                if (current == null) current = utilRule.createMyEmfObject()
                literalAssignment(current!!)
            } else {
                val newObject = createEmfObjectIfPossible(it)
                if (newObject != null) {
                    val assigment = utilRule.findAssignment(it)
                    if (assigment != null) {
                        if (current == null) current = utilRule.createMyEmfObject()
                        assigment(current!!, newObject)
                    } else
                        current = newObject
                } else if (isCrossReference(it)) {
                    if (current == null) current = utilRule.createMyEmfObject()
                    createCrossReference(it, current!!)
                }
            }
        }
        return current
    }
}