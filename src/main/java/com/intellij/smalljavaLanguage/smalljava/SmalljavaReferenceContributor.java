package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.smalljavaLanguage.smalljava.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class SmalljavaReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(SmalljavaREFERENCESJClassQualifiedName.class).withLanguage(SmalljavaLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        SmalljavaREFERENCESJClassQualifiedName reference = (SmalljavaREFERENCESJClassQualifiedName) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(SmalljavaSJClass.class));
                        return new PsiReference[]{
                                new SmalljavaReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(SmalljavaREFERENCESJMemberID.class).withLanguage(SmalljavaLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        SmalljavaREFERENCESJMemberID reference = (SmalljavaREFERENCESJMemberID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(SmalljavaSJMember.class));
                        return new PsiReference[]{
                                new SmalljavaReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(SmalljavaREFERENCESJSymbolID.class).withLanguage(SmalljavaLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        SmalljavaREFERENCESJSymbolID reference = (SmalljavaREFERENCESJSymbolID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(SmalljavaSJSymbol.class));
                        return new PsiReference[]{
                                new SmalljavaReference(element, new TextRange(0, value.length()), list)};
                    }
                });
    }
}