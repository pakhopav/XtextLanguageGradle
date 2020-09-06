package com.intellij.statLanguage.stat.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

public abstract class StatNamedElementImpl extends StatPsiCompositeElementImpl implements PsiNameIdentifierOwner {
    public StatNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}