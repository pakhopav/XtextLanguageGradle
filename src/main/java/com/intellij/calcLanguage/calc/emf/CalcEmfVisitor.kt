package com.intellij.calcLanguage.calc.emf


import arithmetics.*
import com.intellij.calcLanguage.calc.emf.util.CalcEmfBridgeUtil
import com.intellij.calcLanguage.calc.psi.*
import com.intellij.entityLanguage.entity.emf.scope.EntityScope
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.PsiWhiteSpaceImpl
import com.intellij.xtextLanguage.xtext.emf.ObjectDescription
import com.intellij.xtextLanguage.xtext.emf.impl.ObjectDescriptionImpl
import org.eclipse.emf.common.util.EList
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
        var current = CalcEmfBridgeUtil.eFACTORY.create(CalcEmfBridgeUtil.ePACKAGE.expression) as Expression
        getAllChildren(psiAddition).forEach {
            val rewrite = utilRule.findRewrite(it)
            if (rewrite != null) current = rewrite(current) as Expression
            val emfObject = createEmfObjectIfPossible(it)
            if (emfObject != null) {
                val assigment = utilRule.findAssignment(it)
                if (assigment != null) assigment(current, emfObject)
                else current = emfObject as Expression
            }
        }
        return current
    }

//    fun visitAddition(psiAddition: calcAddition): Expression? {
//        var current = visitMultiplication(psiAddition.multiplicationList[0])
//        val keywords = util.getKeywordsList(psiAddition)
//        for (i in 1 until psiAddition.multiplicationList.size) {
//            var temp: Expression
//            if (keywords[i - 1].text == "+") {
//                temp = factory.create(ePackage.plus) as Plus
//                temp.eSet(ePackage.plus_Left, current)
//                temp.eSet(ePackage.plus_Right, visitMultiplication(psiAddition.multiplicationList[i]))
//            } else {
//                temp = factory.create(ePackage.minus) as Minus
//                temp.eSet(ePackage.minus_Left, current)
//                temp.eSet(ePackage.minus_Right, visitMultiplication(psiAddition.multiplicationList[i]))
//            }
//            current = temp
//        }
//        return current
//    }

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
        return visitAddition(psiExpression.addition)
    }

    fun visitImport(psiImport: calcImport): Import {
        val utilRule = CalcEmfBridgeUtil.IMPORT
        var current = CalcEmfBridgeUtil.eFACTORY.create(CalcEmfBridgeUtil.ePACKAGE.import) as Import
        getAllChildren(psiImport).forEach {
            val rewrite = utilRule.findRewrite(it)
            if (rewrite != null) current = rewrite(current) as Import
            val emfObject = createEmfObjectIfPossible(it)
            if (emfObject != null) {
                val assigment = utilRule.findAssignment(it)
                if (assigment != null) assigment(current, emfObject)
                else current = emfObject as Import
            }
        }
        return current
    }

    fun visitModule(psiModule: calcModule): Module {
        val utilRule = CalcEmfBridgeUtil.MODULE
        var current = CalcEmfBridgeUtil.eFACTORY.create(CalcEmfBridgeUtil.ePACKAGE.module) as Module
        getAllChildren(psiModule).forEach {
            val rewrite = utilRule.findRewrite(it)
            if (rewrite != null) current = rewrite(current) as Module
            val emfObject = createEmfObjectIfPossible(it)
            if (emfObject != null) {
                val assigment = utilRule.findAssignment(it)
                if (assigment != null) current?.let { assigment(it, emfObject) }
                else current = emfObject as Module
            }
        }
        return current
    }

    fun visitMultiplication(psiMultiplication: calcMultiplication): Expression? {
        val utilRule = CalcEmfBridgeUtil.MULTIPLICATION
        var current = CalcEmfBridgeUtil.eFACTORY.create(CalcEmfBridgeUtil.ePACKAGE.expression) as Expression
        getAllChildren(psiMultiplication).forEach {
            val rewrite = utilRule.findRewrite(it)
            if (rewrite != null) current = rewrite(current) as Expression
            val emfObject = createEmfObjectIfPossible(it)
            if (emfObject != null) {
                val assigment = utilRule.findAssignment(it)
                if (assigment != null) current?.let { assigment(it, emfObject) }
                else current = emfObject as Expression
            }
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
        visitREFERENCEAbstractDefinitionID(psiPrimaryExpression3.referenceAbstractDefinitionID)
        return current
    }

    fun visitREFERENCEAbstractDefinitionID(psiAbstractDefinitionID: calcREFERENCEAbstractDefinitionID) {
        referencedAbstractDefinitions.put(psiAbstractDefinitionID.context as FunctionCall, psiAbstractDefinitionID.text)
    }

    fun visitREFERENCEModuleID(psiModuleID: calcREFERENCEModuleID) {
        referencedModules.put(psiModuleID.context as Import, psiModuleID.text)
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
        } else if (psiElement is calcREFERENCEModuleID) {
            visitREFERENCEModuleID(psiElement)
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
}