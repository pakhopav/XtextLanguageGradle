package com.intellij.calcLanguage.calc.emf


import arithmetics.ArithmeticsPackage
import arithmetics.FunctionCall
import arithmetics.Import
import arithmetics.Module
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


    fun createModel(psiModule: calcModule): Module? {
        emfRoot = visitElement(psiModule) as Module
        completeRawModel()
        return emfRoot
    }

    private fun visitREFERENCEAbstractDefinitionID(psiAbstractDefinitionID: calcREFERENCEAbstractDefinitionID, functionCall: FunctionCall) {
        referencedAbstractDefinitions.put(functionCall, psiAbstractDefinitionID.text)
    }

    private fun visitREFERENCEModuleID(psiModuleID: calcREFERENCEModuleID, import: Import) {
        referencedModules.put(import, psiModuleID.text)
    }

    private fun completeRawModel() {
        val scope = EntityScope(modelDescriptions)
        referencedAbstractDefinitions.forEach {
            val container = it.key
            val resolvedDefinition = scope.getSingleElement(it.value)?.obj
            resolvedDefinition?.let { container.eSet(ArithmeticsPackage.eINSTANCE.functionCall_Func, resolvedDefinition) }
        }

        referencedModules.forEach {
            val container = it.key
            val resolvedModule = scope.getSingleElement(it.value)?.obj
            resolvedModule?.let { container.eSet(ArithmeticsPackage.eINSTANCE.import_Module, resolvedModule) }
        }
    }


    private fun getUtilRuleClass(psiElement: PsiElement): EmfBridgeRule {
        if (psiElement is calcModule) {
            return MODULE
        } else if (psiElement is calcMultiplication) {
            return MULTIPLICATION
        } else if (psiElement is calcPrimaryExpression) {
            return PRIMARY_EXPRESSION
        } else if (psiElement is calcImport) {
            return IMPORT
        } else if (psiElement is calcStatement) {
            return STATEMENT
        } else if (psiElement is calcDeclaredParameter) {
            return DECLARED_PARAMETER
        } else if (psiElement is calcExpression) {
            return EXPRESSION
        } else if (psiElement is calcPrimaryExpression1) {
            return PRIMARY_EXPRESSION1
        } else if (psiElement is calcPrimaryExpression2) {
            return PRIMARY_EXPRESSION2
        } else if (psiElement is calcPrimaryExpression3) {
            return PRIMARY_EXPRESSION3
        } else if (psiElement is calcEvaluation) {
            return EVALUATION
        } else if (psiElement is calcDefinition) {
            return DEFINITION
        } else {
            return ADDITION
        }
    }

    private fun getAllChildren(psiElement: PsiElement): List<PsiElement> {
        var temp: PsiElement? = psiElement.firstChild
        val result = mutableListOf<PsiElement>()
        while (temp != null) {
            if (temp !is PsiWhiteSpaceImpl) result.add(temp)
            temp = temp.nextSibling
        }
        return result
    }

    private fun isCrossReference(psiElement: PsiElement): Boolean {
        return psiElement is calcREFERENCEAbstractDefinitionID || psiElement is calcREFERENCEModuleID
    }

    private fun createCrossReference(psiElement: PsiElement, container: EObject) {
        if (psiElement is calcREFERENCEModuleID) visitREFERENCEModuleID(psiElement, container as Import)
        else if (psiElement is calcREFERENCEAbstractDefinitionID) visitREFERENCEAbstractDefinitionID(psiElement, container as FunctionCall)
    }

    private fun visitElement(element: PsiElement): EObject? {
        val utilRule = getUtilRuleClass(element)
        var current: EObject? = null
        getAllChildren(element).forEach {
            val rewrite = utilRule.findRewrite(it)
            if (rewrite != null) current = rewrite.rewrite(current)
            val literalAssignment = utilRule.findLiteralAssignment(it)
            if (literalAssignment != null) {
                if (current == null) current = utilRule.createObject()
                literalAssignment.assign(current!!)
                if (literalAssignment.isName()) {
                    modelDescriptions.add(ObjectDescriptionImpl(current!!, it.text))
                }
            } else {
                val newObject = visitElement(it)
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