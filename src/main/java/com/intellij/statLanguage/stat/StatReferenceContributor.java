package com.intellij.statLanguage.stat;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.statLanguage.stat.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class StatReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(StatREFERENCEEventID.class).withLanguage(StatLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        StatREFERENCEEventID reference = (StatREFERENCEEventID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(StatEvent.class));
                        return new PsiReference[]{
                                new StatReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(StatREFERENCECommandID.class).withLanguage(StatLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        StatREFERENCECommandID reference = (StatREFERENCECommandID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(StatCommand.class));
                        return new PsiReference[]{
                                new StatReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(StatREFERENCEStateID.class).withLanguage(StatLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        StatREFERENCEStateID reference = (StatREFERENCEStateID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(StatState.class));
                        return new PsiReference[]{
                                new StatReference(element, new TextRange(0, value.length()), list)};
                    }
                });
    }
}