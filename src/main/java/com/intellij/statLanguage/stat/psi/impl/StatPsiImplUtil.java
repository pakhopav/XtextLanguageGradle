package com.intellij.statLanguage.stat.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.statLanguage.stat.psi.StatCommand;
import com.intellij.statLanguage.stat.psi.StatEvent;
import com.intellij.statLanguage.stat.psi.StatState;

import java.util.Optional;

public class StatPsiImplUtil {
    public static PsiElement setName(StatEvent element, String newName) {
        //TODO
        return element;
    }

    public static String getName(StatEvent element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(StatEvent element) {
        return element.getId();

    }                        
    public static PsiElement setName(StatCommand element, String newName) {
        //TODO
        return element;
    }

    public static String getName(StatCommand element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(StatCommand element) {
        return element.getId();

    }                        
    public static PsiElement setName(StatState element, String newName) {
        //TODO
        return element;
    }

    public static String getName(StatState element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(StatState element) {
        return element.getId();

    }
}    
