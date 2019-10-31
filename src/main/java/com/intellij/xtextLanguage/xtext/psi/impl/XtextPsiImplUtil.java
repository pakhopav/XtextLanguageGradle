package com.intellij.xtextLanguage.xtext.psi.impl;

import com.intellij.psi.PsiElement;
import com.intellij.xtextLanguage.xtext.psi.*;

import java.util.Optional;
            
public class XtextPsiImplUtil {
    static XtextNameVisitor nameVisitor = new XtextNameVisitor();

    public static PsiElement setName(XtextGrammar element, String newName) {
        //TODO
        return element;
    }

    public static String getName(XtextGrammar element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(XtextGrammar element) {
        return nameVisitor.visitGrammar(element);
    }

    public static PsiElement setName(XtextAbstractRule element, String newName) {
        //TODO
        return element;
    }

    public static String getName(XtextAbstractRule element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(XtextAbstractRule element) {
        return nameVisitor.visitAbstractRule(element);
    }

    public static PsiElement setName(XtextAbstractMetamodelDeclaration element, String newName) {
        //TODO
        return element;
    }

    public static String getName(XtextAbstractMetamodelDeclaration element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(XtextAbstractMetamodelDeclaration element) {
        return nameVisitor.visitAbstractMetamodelDeclaration(element);
    }

    public static PsiElement setName(XtextParameter element, String newName) {
        //TODO
        return element;
    }

    public static String getName(XtextParameter element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(XtextParameter element) {
        return nameVisitor.visitParameter(element);
    }
}