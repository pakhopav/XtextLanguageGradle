package com.intellij.calcLanguage.calc.psi.impl;

import com.intellij.calcLanguage.calc.psi.CalcAbstractDefinition;
import com.intellij.calcLanguage.calc.psi.CalcModule;
import com.intellij.calcLanguage.calc.psi.CalcNameVisitor;
import com.intellij.psi.PsiElement;

import java.util.Optional;

public class CalcPsiImplUtil {
    static CalcNameVisitor nameVisitor = new CalcNameVisitor();

    public static PsiElement setName(CalcModule element, String newName) {
        //TODO
        return element;
    }

    public static String getName(CalcModule element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(CalcModule element) {
        return nameVisitor.visitModule(element);
    }

    public static PsiElement setName(CalcAbstractDefinition element, String newName) {
        //TODO
        return element;
    }

    public static String getName(CalcAbstractDefinition element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(CalcAbstractDefinition element) {
        return nameVisitor.visitAbstractDefinition(element);
    }
}