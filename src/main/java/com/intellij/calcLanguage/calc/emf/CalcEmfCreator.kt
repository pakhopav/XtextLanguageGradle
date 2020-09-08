package com.intellij.calcLanguage.calc.emf
import arithmetics.*
import com.intellij.calcLanguage.calc.emf.scope.CalcScope
import com.intellij.calcLanguage.calc.psi.*
import com.intellij.psi.PsiElement
import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
import com.intellij.xtextLanguage.xtext.emf.EmfCreator
import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
import org.eclipse.emf.ecore.EObject

class CalcEmfCreator : EmfCreator() {
    private val MODULE = CalcModuleBridgeRule()
    private val IMPORT = CalcImportBridgeRule()
    private val STATEMENT = CalcStatementBridgeRule()
    private val DEFINITION = CalcDefinitionBridgeRule()
    private val DECLAREDPARAMETER = CalcDeclaredParameterBridgeRule()
    private val ABSTRACTDEFINITION = CalcAbstractDefinitionBridgeRule()
    private val EVALUATION = CalcEvaluationBridgeRule()
    private val EXPRESSION = CalcExpressionBridgeRule()
    private val ADDITION = CalcAdditionBridgeRule()
    private val MULTIPLICATION = CalcMultiplicationBridgeRule()
    private val PRIMARYEXPRESSION1 = CalcPrimaryExpression1BridgeRule()
    private val PRIMARYEXPRESSION2 = CalcPrimaryExpression2BridgeRule()
    private val PRIMARYEXPRESSION3 = CalcPrimaryExpression3BridgeRule()
    private val PRIMARYEXPRESSION = CalcPrimaryExpressionBridgeRule()
    private val importToModuleNameList = mutableListOf<Pair<Import, String>>()
    private val functionCallToAbstractDefinitionNameList = mutableListOf<Pair<FunctionCall, String>>()
    override fun getBridgeRuleForPsiElement(psiElement: PsiElement): EmfBridgeRule? {
        if (psiElement is CalcModule) {
            return MODULE
        }
        if (psiElement is CalcImport) {
            return IMPORT
        }
        if (psiElement is CalcStatement) {
            return STATEMENT
        }
        if (psiElement is CalcDefinition) {
            return DEFINITION
        }
        if (psiElement is CalcDeclaredParameter) {
            return DECLAREDPARAMETER
        }
        if (psiElement is CalcAbstractDefinition) {
            return ABSTRACTDEFINITION
        }
        if (psiElement is CalcEvaluation) {
            return EVALUATION
        }
        if (psiElement is CalcExpression) {
            return EXPRESSION
        }
        if (psiElement is CalcAddition) {
            return ADDITION
        }
        if (psiElement is CalcMultiplication) {
            return MULTIPLICATION
        }
        if (psiElement is CalcPrimaryExpression1) {
            return PRIMARYEXPRESSION1
        }
        if (psiElement is CalcPrimaryExpression2) {
            return PRIMARYEXPRESSION2
        }
        if (psiElement is CalcPrimaryExpression3) {
            return PRIMARYEXPRESSION3
        }
        if (psiElement is CalcPrimaryExpression) {
            return PRIMARYEXPRESSION
        }
        if (psiElement is CalcMultiplicationAdditionRight) {
            return MULTIPLICATION
        }
        if (psiElement is CalcPrimaryExpressionMultiplicationRight) {
            return PRIMARYEXPRESSION
        } 
        return null
    }
    override fun registerObject(obj: EObject?, descriptions: MutableCollection<ObjectDescription>) {
        obj?.let {
            if (obj is Module) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" } 
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is Definition) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" } 
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else if (obj is DeclaredParameter) {
                val feature = obj.eClass().eAllStructuralFeatures.firstOrNull { it.name == "name" } 
                descriptions.add(ObjectDescriptionImpl(it, it.eGet(feature) as String))
            } else return
        }
    }
    override fun completeRawModel() {
        val scope = CalcScope(modelDescriptions)
        importToModuleNameList.forEach {
            val container = it.first
            val resolvedDefinition = scope.getSingleElement(it.second)?.obj
            val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "module" }
            resolvedDefinition?.let {
                container.eSet(feature, it)
            }
        }
        functionCallToAbstractDefinitionNameList.forEach {
            val container = it.first
            val resolvedDefinition = scope.getSingleElement(it.second)?.obj
            val feature = container.eClass().eAllStructuralFeatures.firstOrNull { it.name == "func" }
            resolvedDefinition?.let {
                container.eSet(feature, it)
            }
        }
    }
    override fun isCrossReference(psiElement: PsiElement): Boolean {
        return psiElement is CalcREFERENCEModuleID || psiElement is CalcREFERENCEAbstractDefinitionID
    }
    override fun createCrossReference(psiElement: PsiElement, container: EObject) {
        if (container is Import && psiElement is CalcREFERENCEModuleID)
            importToModuleNameList.add(Pair(container, psiElement.text))
        else if (container is FunctionCall && psiElement is CalcREFERENCEAbstractDefinitionID)
            functionCallToAbstractDefinitionNameList.add(Pair(container, psiElement.text))
    }
}