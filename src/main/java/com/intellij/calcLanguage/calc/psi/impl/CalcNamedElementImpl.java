package com.intellij.calcLanguage.calc.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

public abstract class CalcNamedElementImpl extends CalcPsiCompositeElementImpl implements PsiNameIdentifierOwner {
    public CalcNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}