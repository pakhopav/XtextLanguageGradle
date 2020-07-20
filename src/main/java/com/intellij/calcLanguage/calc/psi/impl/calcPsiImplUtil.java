package com.intellij.calcLanguage.calc.psi.impl;

import com.intellij.calcLanguage.calc.psi.calcAbstractDefinition;
import com.intellij.calcLanguage.calc.psi.calcModule;
import com.intellij.calcLanguage.calc.psi.calcNameVisitor;
import com.intellij.psi.PsiElement;

import java.util.Optional;

public class calcPsiImplUtil {
    static calcNameVisitor nameVisitor = new calcNameVisitor();
    public static PsiElement setName(calcModule element, String newName) {
        //TODO
        return element;
    }

    public static String getName(calcModule element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(calcModule element) {
        return nameVisitor.visitModule(element);
    }

    public static PsiElement setName(calcAbstractDefinition element, String newName) {
        //TODO
        return element;
    }

    public static String getName(calcAbstractDefinition element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(calcAbstractDefinition element) {
        return nameVisitor.visitAbstractDefinition(element);
    }
}