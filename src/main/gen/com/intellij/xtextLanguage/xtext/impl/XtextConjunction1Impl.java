// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextConjunction1;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import org.jetbrains.annotations.NotNull;

public class XtextConjunction1Impl extends XtextConjunctionImpl implements XtextConjunction1 {

    public XtextConjunction1Impl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitConjunction1(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

}
