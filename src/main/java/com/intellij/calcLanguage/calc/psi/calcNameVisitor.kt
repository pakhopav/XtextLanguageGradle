package com.intellij.calcLanguage.calc.psi

import com.intellij.psi.PsiElement

class calcNameVisitor {
    fun visitModule(node: calcModule): PsiElement? {
        return node.id
    }

    fun visitImport(node: calcImport): PsiElement? {
        return null
    }

    fun visitStatement(node: calcStatement): PsiElement? {
        node.definition?.let { visitDefinition(it)?.let { return@visitStatement it } }
        node.evaluation?.let { visitEvaluation(it)?.let { return@visitStatement it } }
        return null
    }

    fun visitDefinition(node: calcDefinition): PsiElement? {
        return node.id
    }

    fun visitDeclaredParameter(node: calcDeclaredParameter): PsiElement? {
        return node.id
    }

    fun visitAbstractDefinition(node: calcAbstractDefinition): PsiElement? {
        node.definition?.let { visitDefinition(it)?.let { return@visitAbstractDefinition it } }
        node.declaredParameter?.let { visitDeclaredParameter(it)?.let { return@visitAbstractDefinition it } }
        return null
    }

    fun visitEvaluation(node: calcEvaluation): PsiElement? {
        node.expression?.let { visitExpression(it)?.let { return@visitEvaluation it } }
        return null
    }

    fun visitExpression(node: calcExpression): PsiElement? {
        node.addition?.let { visitAddition(it)?.let { return@visitExpression it } }
        return null
    }

    fun visitAddition(node: calcAddition): PsiElement? {
        node.multiplicationList.forEach { visitMultiplication(it)?.let { return@visitAddition it } }
        return null
    }

    fun visitMultiplication(node: calcMultiplication): PsiElement? {
        node.primaryExpressionList.forEach { visitPrimaryExpression(it)?.let { return@visitMultiplication it } }
        return null
    }

    fun visitPrimaryExpression1(node: calcPrimaryExpression1): PsiElement? {
        node.expression?.let { visitExpression(it)?.let { return@visitPrimaryExpression1 it } }
        return null
    }

    fun visitPrimaryExpression2(node: calcPrimaryExpression2): PsiElement? {
        return null
    }

    fun visitPrimaryExpression3(node: calcPrimaryExpression3): PsiElement? {
        return null
    }

    fun visitPrimaryExpression(node: calcPrimaryExpression): PsiElement? {
        node.primaryExpression1?.let { visitPrimaryExpression1(it)?.let { return@visitPrimaryExpression it } }
        node.primaryExpression2?.let { visitPrimaryExpression2(it)?.let { return@visitPrimaryExpression it } }
        node.primaryExpression3?.let { visitPrimaryExpression3(it)?.let { return@visitPrimaryExpression it } }
        return null
    }
}