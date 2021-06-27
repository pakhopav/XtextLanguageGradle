// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAlternatives;
import com.intellij.xtextLanguage.xtext.psi.XtextAlternativesSuffix1;
import com.intellij.xtextLanguage.xtext.psi.XtextConditionalBranch;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextAlternativesImpl extends XtextPsiCompositeElementImpl implements XtextAlternatives {

  public XtextAlternativesImpl(@NotNull ASTNode node) {
    super(node);
  }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitAlternatives(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public XtextAlternativesSuffix1 getAlternativesSuffix1() {
        return findChildByClass(XtextAlternativesSuffix1.class);
    }

    @Override
    @NotNull
    public XtextConditionalBranch getConditionalBranch() {
        return findNotNullChildByClass(XtextConditionalBranch.class);
    }

}
