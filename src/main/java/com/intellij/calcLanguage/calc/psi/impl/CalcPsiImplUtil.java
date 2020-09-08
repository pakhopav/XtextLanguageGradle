package com.intellij.calcLanguage.calc.psi.impl;

import com.intellij.calcLanguage.calc.psi.CalcAbstractDefinition;
import com.intellij.calcLanguage.calc.psi.CalcDeclaredParameter;
import com.intellij.calcLanguage.calc.psi.CalcDefinition;
import com.intellij.calcLanguage.calc.psi.CalcModule;
import com.intellij.psi.PsiElement;

import java.util.Optional;

public class CalcPsiImplUtil {
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
        return element.getId();

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
        if (element instanceof CalcDefinition) {
            return ((CalcDefinition) element).getId();
        }
        if (element instanceof CalcDeclaredParameter) {
            return ((CalcDeclaredParameter) element).getId();
        }
        return null;
    }
}    
