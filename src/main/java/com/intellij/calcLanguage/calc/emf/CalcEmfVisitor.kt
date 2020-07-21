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

    fun visitAddition(psiAddition: calcAddition): EObject? {
        val utilRule = AdditionRule()
        return visitElement(psiAddition, utilRule)
    }


    fun visitDeclaredParameter(psiDeclaredParameter: calcDeclaredParameter): EObject {
        val utilRule = DeclaredParameterRule()
        val declaredParameter = visitElement(psiDeclaredParameter, utilRule) as DeclaredParameter
        modelDescriptions.add(ObjectDescriptionImpl(declaredParameter, declaredParameter.eGet(ePackage.abstractDefinition_Name).toString()))
        return declaredParameter
    }

    fun visitDefinition(psiDefinition: calcDefinition): EObject {
        val utilRule = DefinitionRule()
        val definition = visitElement(psiDefinition, utilRule)!!
        modelDescriptions.add(ObjectDescriptionImpl(definition, definition.eGet(ePackage.abstractDefinition_Name).toString()))
        return definition
    }

    fun visitEvaluation(psiEvaluation: calcEvaluation): EObject? {
        val utilRule = EvaluationRule()
        return visitElement(psiEvaluation, utilRule)
    }

    fun visitExpression(psiExpression: calcExpression): EObject? {
        val utilRule = ExpressionRule()
        return visitElement(psiExpression, utilRule)
    }

    fun visitImport(psiImport: calcImport): EObject? {
        val utilRule = ImportRule()
        return visitElement(psiImport, utilRule)
    }

    fun visitModule(psiModule: calcModule): EObject? {
        val utilRule = ModuleRule()
        emfRoot = visitElement(psiModule, utilRule) as Module
        modelDescriptions.add(ObjectDescriptionImpl(emfRoot!!, emfRoot!!.eGet(ePackage.module_Name).toString()))
        return emfRoot
    }

    fun visitMultiplication(psiMultiplication: calcMultiplication): EObject? {
        val utilRule = MultiplicationRule()
        return visitElement(psiMultiplication, utilRule)
    }

    fun visitPrimaryExpression(psiPrimaryExpression: calcPrimaryExpression): EObject? {
        val utilRule = PrimaryExpressionRule()
        return visitElement(psiPrimaryExpression, utilRule)
    }

    fun visitPrimaryExpression1(psiPrimaryExpression1: calcPrimaryExpression1): EObject? {
        val utilRule = PrimaryExpression1Rule()
        return visitElement(psiPrimaryExpression1, utilRule)
    }

    fun visitPrimaryExpression2(psiPrimaryExpression2: calcPrimaryExpression2): EObject? {
        val utilRule = PrimaryExpression2Rule()
        return visitElement(psiPrimaryExpression2, utilRule)
    }

    fun visitPrimaryExpression3(psiPrimaryExpression3: calcPrimaryExpression3): EObject? {
        val utilRule = PrimaryExpression3Rule()
        return visitElement(psiPrimaryExpression3, utilRule)
    }

    fun visitStatement(psiStatement: calcStatement): EObject? {
        val utilRule = StatementRule()
        return visitElement(psiStatement, utilRule)
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
            if (rewrite != null) current = rewrite.rewrite(current)
            val literalAssignment = utilRule.findLiteralAssignment(it)
            if (literalAssignment != null) {
                if (current == null) current = utilRule.createObject()
                literalAssignment.assign(current!!)
            } else {
                val newObject = createEmfObjectIfPossible(it)
                if (newObject != null) {
                    val assigment = utilRule.findObjectAssignment(it)
                    if (assigment != null) {
                        if (current == null) current = utilRule.createObject()
                        assigment.assign(current!!, newObject)
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