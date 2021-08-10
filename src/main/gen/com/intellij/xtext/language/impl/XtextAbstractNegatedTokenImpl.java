// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextAbstractNegatedToken;
import com.intellij.xtext.language.psi.XtextNegatedToken;
import com.intellij.xtext.language.psi.XtextUntilToken;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextAbstractNegatedTokenImpl extends XtextPsiCompositeElementImpl implements XtextAbstractNegatedToken {

  public XtextAbstractNegatedTokenImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAbstractNegatedToken(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @Nullable
  public XtextNegatedToken getNegatedToken() {
    return findChildByClass(XtextNegatedToken.class);
  }

  @Override
  @Nullable
  public XtextUntilToken getUntilToken() {
    return findChildByClass(XtextUntilToken.class);
  }

}
