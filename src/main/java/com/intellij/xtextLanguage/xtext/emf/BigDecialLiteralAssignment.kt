package com.intellij.xtextLanguage.xtext.emf

import com.intellij.psi.PsiElement
import org.eclipse.emf.ecore.EObject
import org.eclipse.emf.ecore.EStructuralFeature
import java.math.BigDecimal

class BigDecialLiteralAssignment(val feature: EStructuralFeature) : LiteralAssignment {
    override fun assign(obj: EObject, literal: PsiElement) {
        obj.eSet(feature, BigDecimal(literal.text))
    }
}