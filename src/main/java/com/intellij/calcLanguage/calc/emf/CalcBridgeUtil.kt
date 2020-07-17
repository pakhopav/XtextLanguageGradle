package com.intellij.calcLanguage.calc.emf

import com.intellij.calcLanguage.calc.psi.calcAddition
import com.intellij.calcLanguage.calc.psi.calcMultiplication
import com.intellij.psi.PsiElement
import com.intellij.psi.impl.source.tree.LeafPsiElement
import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.emf.BridgeUtil
import kotlin.streams.toList

class CalcBridgeUtil : BridgeUtil() {
    fun getKeywordsList(psiAddition: calcAddition): List<PsiElement> {
        return PsiTreeUtil.findChildrenOfType(psiAddition, LeafPsiElement::class.java).stream()
                .filter { it.text == "+" || it.text == "-" }
                .toList()
    }

    fun getKeywordsList(psiAddition: calcMultiplication): List<PsiElement> {
        return PsiTreeUtil.findChildrenOfType(psiAddition, LeafPsiElement::class.java).stream()
                .filter { it.text == "*" || it.text == "/" }
                .toList()
    }
}