package com.intellij.entityLanguage.entity;

import com.intellij.entityLanguage.entity.psi.EntityEntity;
import com.intellij.entityLanguage.entity.psi.EntityREFERENCEEntityID;
import com.intellij.entityLanguage.entity.psi.EntityREFERENCETypeID;
import com.intellij.entityLanguage.entity.psi.EntityType;
import com.intellij.openapi.util.TextRange;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.psi.*;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class EntityReferenceContributor extends PsiReferenceContributor {
    @Override
    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(EntityREFERENCEEntityID.class).withLanguage(EntityLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        EntityREFERENCEEntityID reference = (EntityREFERENCEEntityID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(EntityEntity.class));
                        return new PsiReference[]{
                                new EntityReference(element, new TextRange(0, value.length()), list)};
                    }
                });
        registrar.registerReferenceProvider(PlatformPatterns.psiElement(EntityREFERENCETypeID.class).withLanguage(EntityLanguage.INSTANCE),
                new PsiReferenceProvider() {
                    @NotNull
                    @Override
                    public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
                                                                 @NotNull ProcessingContext context) {
                        EntityREFERENCETypeID reference = (EntityREFERENCETypeID) element;
                        String value = reference.getText();
                        ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>) Arrays.asList(EntityType.class));
                        return new PsiReference[]{
                                new EntityReference(element, new TextRange(0, value.length()), list)};
                    }
                });
    }
}