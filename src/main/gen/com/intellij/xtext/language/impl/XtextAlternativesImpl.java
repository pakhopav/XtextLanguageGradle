// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextAlternatives;
import com.intellij.xtext.language.psi.XtextAlternativesSuffix1;
import com.intellij.xtext.language.psi.XtextConditionalBranch;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
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
