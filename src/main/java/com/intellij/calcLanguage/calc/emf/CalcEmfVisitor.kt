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


class CalcEmfVisitor {

    private var emfRoot: Module? = null
    private var referencedAbstractDefinitions = mutableMapOf<FunctionCall, String>()
    private var referencedModules = mutableMapOf<Import, String>()
    private var modelDescriptions = mutableListOf<ObjectDescription>()
    private val ePackage = ArithmeticsPackage.eINSTANCE


    fun createModel(psiModule: calcModule): Module? {
        visitModule(psiModule)
        completeRawModel()
        return emfRoot
    }

    fun visitAddition(psiAddition: calcAddition): Expression {
        val utilRule = AdditionRule()
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
        val utilRule = ExpressionRule()
        return visitElement(psiExpression, utilRule) as Expression
    }

    fun visitImport(psiImport: calcImport): Import {
        val utilRule = ImportRule()
        return visitElement(psiImport, utilRule) as Import
    }

    fun visitModule(psiModule: calcModule): Module? {
        val utilRule = ModuleRule()
        emfRoot = visitElement(psiModule, utilRule) as Module
        modelDescriptions.add(ObjectDescriptionImpl(emfRoot!!, emfRoot!!.eGet(ePackage.module_Name).toString()))
        return emfRoot
    }

    fun visitMultiplication(psiMultiplication: calcMultiplication): Expression {
        val utilRule = MultiplicationRule()
        return visitElement(psiMultiplication, utilRule) as Expression
    }

    fun visitPrimaryExpression(psiPrimaryExpression: calcPrimaryExpression): Expression {
        val utilRule = PrimaryExpressionRule()
        return visitElement(psiPrimaryExpression, utilRule) as Expression
    }

    fun visitPrimaryExpression1(psiPrimaryExpression1: calcPrimaryExpression1): Expression {
        val utilRule = PrimaryExpression1Rule()
        return visitElement(psiPrimaryExpression1, utilRule) as Expression
    }

    fun visitPrimaryExpression2(psiPrimaryExpression2: calcPrimaryExpression2): NumberLiteral {
        val utilRule = PrimaryExpression2Rule()
        return visitElement(psiPrimaryExpression2, utilRule) as NumberLiteral
    }

    fun visitPrimaryExpression3(psiPrimaryExpression3: calcPrimaryExpression3): FunctionCall {
        val utilRule = PrimaryExpression3Rule()
        return visitElement(psiPrimaryExpression3, utilRule) as FunctionCall
    }

    fun visitStatement(psiStatement: calcStatement): Statement {
        val utilRule = StatementRule()
        return visitElement(psiStatement, utilRule) as Statement
    }

    fun visitREFERENCEAbstractDefinitionID(psiAbstractDefinitionID: calcREFERENCEAbstractDefinitionID, functionCall: FunctionCall) {
        referencedAbstractDefinitions.put(functionCall, psiAbstractDefinitionID.text)
    }

    fun visitREFERENCEModuleID(psiModuleID: calcREFERENCEModuleID, import: Import) {
        referencedModules.put(import, psiModuleID.text)
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
        } else if (psiElement is calcPrimaryExpression1) {
            return visitPrimaryExpression1(psiElement)
        } else if (psiElement is calcPrimaryExpression2) {
            return visitPrimaryExpression2(psiElement)
        } else if (psiElement is calcPrimaryExpression3) {
            return visitPrimaryExpression3(psiElement)
        } else if (psiElement is calcEvaluation) {
            return visitEvaluation(psiElement)
        } else if (psiElement is calcDefinition) {
            return visitDefinition(psiElement)
        } else if (psiElement is calcAddition) {
            return visitAddition(psiElement)
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
                if (current == null) current = utilRule.createObject()
                literalAssignment(current!!)
            } else {
                val newObject = createEmfObjectIfPossible(it)
                if (newObject != null) {
                    val assigment = utilRule.findAssignment(it)
                    if (assigment != null) {
                        if (current == null) current = utilRule.createObject()
                        assigment(current!!, newObject)
                    } else
                        current = newObject
                } else if (isCrossReference(it)) {
                    if (current == null) current = utilRule.createObject()
                    createCrossReference(it, current!!)
                }
            }
        }
        return current
    }
}