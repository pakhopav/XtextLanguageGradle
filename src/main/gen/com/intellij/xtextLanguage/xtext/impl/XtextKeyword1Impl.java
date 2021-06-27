// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextKeyword1;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import org.jetbrains.annotations.NotNull;

public class XtextKeyword1Impl extends XtextKeywordImpl implements XtextKeyword1 {

    public XtextKeyword1Impl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitKeyword1(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

}
