package com.intellij.entityLanguage.entity.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

public abstract class EntityNamedElementImpl extends EntityPsiCompositeElementImpl implements PsiNameIdentifierOwner {
    public EntityNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}