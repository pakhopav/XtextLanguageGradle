package com.intellij.calcLanguage.calc;

import com.intellij.calcLanguage.calc.psi.CalcAbstractDefinition;
import com.intellij.calcLanguage.calc.psi.CalcModule;
import com.intellij.calcLanguage.calc.psi.CalcREFERENCEAbstractDefinitionID;
import com.intellij.calcLanguage.calc.psi.CalcREFERENCEModuleID;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class CalcReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CalcREFERENCEModuleID.class).withLanguage(CalcLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        CalcREFERENCEModuleID reference = (CalcREFERENCEModuleID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(CalcModule.class));
                        return new PsiReference[]{
                                new CalcReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(CalcREFERENCEAbstractDefinitionID.class).withLanguage(CalcLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        CalcREFERENCEAbstractDefinitionID reference = (CalcREFERENCEAbstractDefinitionID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(CalcAbstractDefinition.class));
                        return new PsiReference[]{
                                new CalcReference(element, new TextRange(0, value.length()), list)};
                    }
                });
    }
}