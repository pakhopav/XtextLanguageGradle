package com.intellij.xtextLanguage.xtext;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.intellij.xtextLanguage.xtext.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
            
public class XtextReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEGrammarGrammarID.class).withLanguage(XtextLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        XtextREFERENCEGrammarGrammarID reference = (XtextREFERENCEGrammarGrammarID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(XtextGrammar.class));
                        return new PsiReference[]{
                                new XtextReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEAbstractRuleRuleID.class).withLanguage(XtextLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        XtextREFERENCEAbstractRuleRuleID reference = (XtextREFERENCEAbstractRuleRuleID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(XtextAbstractRule.class));
                        return new PsiReference[]{
                                new XtextReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEAbstractMetamodelDeclaration.class).withLanguage(XtextLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        XtextREFERENCEAbstractMetamodelDeclaration reference = (XtextREFERENCEAbstractMetamodelDeclaration) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(XtextAbstractMetamodelDeclaration.class));
                        return new PsiReference[]{
                                new XtextReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEParameterID.class).withLanguage(XtextLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        XtextREFERENCEParameterID reference = (XtextREFERENCEParameterID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(XtextParameter.class));
                        return new PsiReference[]{
                                new XtextReference(element, new TextRange(0, value.length()), list)};
                    }
                });
    }
}