package com.intellij.xtextLanguage.xtext.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.xtextLanguage.xtext.psi.XtextNamedElement;
import org.jetbrains.annotations.NotNull;

public abstract class XtextNamedElementImpl extends XtextPsiCompositeElementImpl implements XtextNamedElement {
    public XtextNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }
}
