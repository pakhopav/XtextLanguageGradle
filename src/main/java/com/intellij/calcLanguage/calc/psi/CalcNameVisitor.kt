package com.intellij.calcLanguage.calc.psi

import com.intellij.psi.PsiElement

class CalcNameVisitor {
    fun visitModule(node: CalcModule): PsiElement? {
        return node.id
    }

    fun visitImport(node: CalcImport): PsiElement? {
        return null
    }

    fun visitStatement(node: CalcStatement): PsiElement? {
        node.definition?.let { visitDefinition(it)?.let { return@visitStatement it } }
        node.evaluation?.let { visitEvaluation(it)?.let { return@visitStatement it } }
        return null
    }

    fun visitDefinition(node: CalcDefinition): PsiElement? {
        return node.id
    }

    fun visitDeclaredParameter(node: CalcDeclaredParameter): PsiElement? {
        return node.id
    }

    fun visitAbstractDefinition(node: CalcAbstractDefinition): PsiElement? {
        node.definition?.let { visitDefinition(it)?.let { return@visitAbstractDefinition it } }
        node.declaredParameter?.let { visitDeclaredParameter(it)?.let { return@visitAbstractDefinition it } }
        return null
    }

    fun visitEvaluation(node: CalcEvaluation): PsiElement? {
        node.expression?.let { visitExpression(it)?.let { return@visitEvaluation it } }
        return null
    }

    fun visitExpression(node: CalcExpression): PsiElement? {
        node.addition?.let { visitAddition(it)?.let { return@visitExpression it } }
        return null
    }

    fun visitPrimaryExpression1(node: CalcPrimaryExpression1): PsiElement? {
        node.expression?.let { visitExpression(it)?.let { return@visitPrimaryExpression1 it } }
        return null
    }

    fun visitPrimaryExpression2(node: CalcPrimaryExpression2): PsiElement? {
        return null
    }

    fun visitPrimaryExpression3(node: CalcPrimaryExpression3): PsiElement? {
        return null
    }

    fun visitPrimaryExpression(node: CalcPrimaryExpression): PsiElement? {
        node.primaryExpression1?.let { visitPrimaryExpression1(it)?.let { return@visitPrimaryExpression it } }
        node.primaryExpression2?.let { visitPrimaryExpression2(it)?.let { return@visitPrimaryExpression it } }
        node.primaryExpression3?.let { visitPrimaryExpression3(it)?.let { return@visitPrimaryExpression it } }
        return null
    }

    fun visitAddition(node: CalcAddition): PsiElement? {
        node.multiplicationAPIList.forEach { visitMultiplicationAPI(it)?.let { return@visitAddition it } }
        return null
    }

    fun visitMultiplication(node: CalcMultiplication): PsiElement? {
        node.primaryExpressionAPIList.forEach { visitPrimaryExpressionAPI(it)?.let { return@visitMultiplication it } }
        return null
    }

    fun visitMultiplicationAdditionRight(node: CalcMultiplicationAdditionRight): PsiElement? {
        node.primaryExpressionAPIList.forEach { visitPrimaryExpressionAPI(it)?.let { return@visitMultiplicationAdditionRight it } }
        return null
    }

    fun visitMultiplicationAPI(node: CalcMultiplicationAPI): PsiElement? {
        node.primaryExpressionAPIList.forEach { visitPrimaryExpressionAPI(it)?.let { return@visitMultiplicationAPI it } }
        return null
    }

    fun visitPrimaryExpressionMultiplicationRight(node: CalcPrimaryExpressionMultiplicationRight): PsiElement? {
        node.primaryExpression1?.let { visitPrimaryExpression1(it)?.let { return@visitPrimaryExpressionMultiplicationRight it } }
        node.primaryExpression2?.let { visitPrimaryExpression2(it)?.let { return@visitPrimaryExpressionMultiplicationRight it } }
        node.primaryExpression3?.let { visitPrimaryExpression3(it)?.let { return@visitPrimaryExpressionMultiplicationRight it } }
        return null
    }

    fun visitPrimaryExpressionAPI(node: CalcPrimaryExpressionAPI): PsiElement? {
        node.primaryExpression1?.let { visitPrimaryExpression1(it)?.let { return@visitPrimaryExpressionAPI it } }
        node.primaryExpression2?.let { visitPrimaryExpression2(it)?.let { return@visitPrimaryExpressionAPI it } }
        node.primaryExpression3?.let { visitPrimaryExpression3(it)?.let { return@visitPrimaryExpressionAPI it } }
        return null
    }
}