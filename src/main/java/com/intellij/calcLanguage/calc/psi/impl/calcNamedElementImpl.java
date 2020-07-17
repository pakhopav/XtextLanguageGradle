package com.intellij.calcLanguage.calc.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

public abstract class calcNamedElementImpl extends calcPsiCompositeElementImpl implements PsiNameIdentifierOwner {
    public calcNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}