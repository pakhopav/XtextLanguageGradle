package com.intellij.entityLanguage.entity.psi.impl;

import com.intellij.entityLanguage.entity.psi.EntityEntity;
import com.intellij.entityLanguage.entity.psi.EntityNameVisitor;
import com.intellij.entityLanguage.entity.psi.EntityType;
import com.intellij.psi.PsiElement;

import java.util.Optional;

public class EntityPsiImplUtil {
    static EntityNameVisitor nameVisitor = new EntityNameVisitor();

    public static PsiElement setName(EntityType element, String newName) {
        //TODO
        return element;
    }

    public static String getName(EntityType element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(EntityType element) {
        return nameVisitor.visitType(element);
    }

    public static PsiElement setName(EntityEntity element, String newName) {
        //TODO
        return element;
    }

    public static String getName(EntityEntity element) {
        return Optional.ofNullable(getNameIdentifier(element))
                .map(PsiElement::getText)
                .orElse(null);
    }

    public static PsiElement getNameIdentifier(EntityEntity element) {
        return nameVisitor.visitEntity(element);
    }
}