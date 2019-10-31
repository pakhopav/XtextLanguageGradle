package com.intellij.xtextLanguage.xtext.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

public abstract class XtextNamedElementImpl extends XtextPsiCompositeElementImpl implements PsiNameIdentifierOwner {
    public XtextNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}