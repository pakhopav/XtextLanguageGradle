package com.intellij.xtext.language.reference;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import com.intellij.xtext.language.XtextLanguage;
import com.intellij.xtext.language.psi.*;
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
//                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(XtextGrammar.class));
                        return new PsiReference[]{
                                new XtextGrammarReference(element, new TextRange(0, value.length()))};
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
//                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(XtextAbstractRule.class));
                        return new PsiReference[]{
                                new XtextRuleReference(element, new TextRange(0, value.length()))};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEAbstractMetamodelDeclarationID.class).withLanguage(XtextLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        XtextREFERENCEAbstractMetamodelDeclarationID reference = (XtextREFERENCEAbstractMetamodelDeclarationID) element;
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
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEEClassifierID.class).withLanguage(XtextLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        XtextREFERENCEEClassifierID reference = (XtextREFERENCEEClassifierID) element;
                        String value = reference.getText();
                        return new PsiReference[]{
                                new XtextEClassifierReference(element, new TextRange(0, value.length()))};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEEPackageSTRING.class).withLanguage(XtextLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        XtextREFERENCEEPackageSTRING reference = (XtextREFERENCEEPackageSTRING) element;
                        String value = reference.getText();
                        return new PsiReference[]{
                                new XtextEPackageReference(element, new TextRange(0, value.length()))};
                    }
                });
//        registrar.registerReferenceProvider(PlatformPatterns.psiElement(XtextREFERENCEEEnumLiteralID.class).withLanguage(XtextLanguage.INSTANCE),
//                new PsiReferenceProvider() {
//                    @NotNull
//                    @Override
//                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
//                                                                 @NotNull ProcessingContext context){
//                        XtextREFERENCEEEnumLiteralID reference = (XtextREFERENCEEEnumLiteralID) element;
//                        String value = reference.getText();
//                        return new PsiReference[]{
//                                new XtextEnumLiteralReference(element, new TextRange(0, value.length()))};
//                    }
//                });
    }
}