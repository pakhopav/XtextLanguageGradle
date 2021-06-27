// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextNegation1;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import org.jetbrains.annotations.NotNull;

public class XtextNegation1Impl extends XtextNegationImpl implements XtextNegation1 {

    public XtextNegation1Impl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitNegation1(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

}
