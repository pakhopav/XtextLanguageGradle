package com.intellij.calcLanguage.calc.emf


import arithmetics.*
import com.intellij.calcLanguage.calc.psi.*
import com.intellij.entityLanguage.entity.emf.scope.EntityScope
import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
import org.eclipse.emf.common.util.EList
import java.math.BigDecimal


class CalcEmfVisitor {

    private var emfRoot: Module? = null
    private var referencedAbstractDefinitions = mutableMapOf<FunctionCall, String>()
    private var referencedModules = mutableMapOf<Import, String>()
    private var modelDescriptions = mutableListOf<ObjectDescription>()
    private val factory = ArithmeticsFactory.eINSTANCE
    private val ePackage = ArithmeticsPackage.eINSTANCE
    private val util = CalcBridgeUtil()


    fun createModel(psiModule: calcModule): Module? {
        visitModule(psiModule)
        completeRawModel()
        return emfRoot
    }


    fun visitAddition(psiAddition: calcAddition): Expression? {
        var current = visitMultiplication(psiAddition.multiplicationList[0])
        val keywords = util.getKeywordsList(psiAddition)
        for (i in 1 until psiAddition.multiplicationList.size) {
            var temp: Expression
            if (keywords[i - 1].text == "+") {
                temp = factory.create(ePackage.plus) as Plus
                temp.eSet(ePackage.plus_Left, current)
                temp.eSet(ePackage.plus_Right, visitMultiplication(psiAddition.multiplicationList[i]))
            } else {
                temp = factory.create(ePackage.minus) as Minus
                temp.eSet(ePackage.minus_Left, current)
                temp.eSet(ePackage.minus_Right, visitMultiplication(psiAddition.multiplicationList[i]))
            }
            current = temp
        }
        return current
    }

    fun visitDeclaredParameter(psiDeclaredParameter: calcDeclaredParameter): DeclaredParameter {
        val current = factory.create(ePackage.declaredParameter) as DeclaredParameter
        current.eSet(ePackage.abstractDefinition_Name, psiDeclaredParameter.id.text)
        modelDescriptions.add(ObjectDescriptionImpl(current, current.eGet(ePackage.abstractDefinition_Name) as String))
        return current
    }

    fun visitDefinition(psiDefinition: calcDefinition): Definition {
        val current = factory.create(ePackage.definition) as Definition
        current.eSet(ePackage.abstractDefinition_Name, psiDefinition.id.text)
        val args = current.eGet(ePackage.definition_Args) as EList<DeclaredParameter>
        psiDefinition.declaredParameterList.forEach { args.add(visitDeclaredParameter(it)) }
        current.eSet(ePackage.definition_Expr, visitExpression(psiDefinition.expression))
        modelDescriptions.add(ObjectDescriptionImpl(current, current.eGet(ePackage.abstractDefinition_Name) as String))
        return current
    }

    fun visitEvaluation(psiEvaluation: calcEvaluation): Evaluation {
        val evaluation = factory.createEvaluation()
        evaluation.eSet(ePackage.evaluation_Expression, visitExpression(psiEvaluation.expression))
        return evaluation
    }

    fun visitExpression(psiExpression: calcExpression): Expression {
        return visitAddition(psiExpression.addition)!!
    }

    fun visitImport(psiImport: calcImport): Import {
        val current = factory.create(ePackage.import) as Import
        visitREFERENCEModuleID(psiImport.referenceModuleID, current)
        return current
    }

    fun visitModule(psiModule: calcModule): Module {
        val module = factory.create(ePackage.module) as Module
        module.eSet(ePackage.module_Name, psiModule.name)
        val statementsList = module.eGet(ePackage.module_Statements) as EList<Statement>
        val importsList = module.eGet(ePackage.module_Imports) as EList<Import>
        psiModule.importList.forEach { importsList.add(visitImport(it)) }
        psiModule.statementList.forEach { statementsList.add(visitStatement(it)) }
        emfRoot = module
        modelDescriptions.add(ObjectDescriptionImpl(module, module.eGet(ePackage.module_Name) as String))
        return module
    }

    fun visitMultiplication(psiMultiplication: calcMultiplication): Expression? {
        var current = visitPrimaryExpression(psiMultiplication.primaryExpressionList[0])
        val keywords = util.getKeywordsList(psiMultiplication)
        for (i in 1 until psiMultiplication.primaryExpressionList.size) {
            var temp: Expression
            if (keywords[i - 1].text == "*") {
                temp = factory.create(ePackage.multi) as Multi
                temp.eSet(ePackage.multi_Left, current)
                temp.eSet(ePackage.multi_Right, visitPrimaryExpression(psiMultiplication.primaryExpressionList[i]))
            } else {
                temp = factory.create(ePackage.div) as Div
                temp.eSet(ePackage.div_Left, current)
                temp.eSet(ePackage.div_Right, visitPrimaryExpression(psiMultiplication.primaryExpressionList[i]))
            }
            current = temp
        }
        return current

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
        val current = factory.create(ePackage.functionCall) as FunctionCall
        val args = current.eGet(ePackage.functionCall_Args) as EList<Expression>
        psiPrimaryExpression3.expressionList.forEach { args.add(visitExpression(it)) }
        visitREFERENCEAbstractDefinitionID(psiPrimaryExpression3.referenceAbstractDefinitionID, current)
        return current
    }

    fun visitREFERENCEAbstractDefinitionID(psiAbstractDefinitionID: calcREFERENCEAbstractDefinitionID, emfFunctionCall: FunctionCall) {
        referencedAbstractDefinitions.put(emfFunctionCall, psiAbstractDefinitionID.text)
    }

    fun visitREFERENCEModuleID(psiModuleID: calcREFERENCEModuleID, emfImport: Import) {
        referencedModules.put(emfImport, psiModuleID.text)
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
}