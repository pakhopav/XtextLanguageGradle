package com.intellij.calcLanguage.calc;

import com.intellij.calcLanguage.calc.psi.calcAbstractDefinition;
import com.intellij.calcLanguage.calc.psi.calcModule;
import com.intellij.calcLanguage.calc.psi.calcREFERENCEAbstractDefinitionID;
import com.intellij.calcLanguage.calc.psi.calcREFERENCEModuleID;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class calcReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(calcREFERENCEModuleID.class).withLanguage(calcLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        calcREFERENCEModuleID reference = (calcREFERENCEModuleID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(calcModule.class));
                        return new PsiReference[]{
                                new calcReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(calcREFERENCEAbstractDefinitionID.class).withLanguage(calcLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        calcREFERENCEAbstractDefinitionID reference = (calcREFERENCEAbstractDefinitionID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(calcAbstractDefinition.class));
                        return new PsiReference[]{
                                new calcReference(element, new TextRange(0, value.length()), list)};
                    }
                });
    }
}