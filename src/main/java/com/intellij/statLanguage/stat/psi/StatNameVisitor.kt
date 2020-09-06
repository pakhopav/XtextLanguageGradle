package com.intellij.statLanguage.stat.psi

import com.intellij.psi.PsiElement

class StatNameVisitor {
    fun visitStatemachine(node: StatStatemachine): PsiElement? {
        return null
    }

    fun visitState(node: StatState): PsiElement? {
        return node.id
    }

    fun visitTransition(node: StatTransition): PsiElement? {
        return null
    }

    fun visitEvent(node: StatEvent): PsiElement? {
        return node.id
    }

    fun visitCommand(node: StatCommand): PsiElement? {
        return node.id
    }
}