// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextAbstractToken;
import com.intellij.xtext.language.psi.XtextAbstractTokenWithCardinality;
import com.intellij.xtext.language.psi.XtextAction;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextAbstractTokenImpl extends XtextPsiCompositeElementImpl implements XtextAbstractToken {

  public XtextAbstractTokenImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAbstractToken(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @Nullable
  public XtextAbstractTokenWithCardinality getAbstractTokenWithCardinality() {
    return findChildByClass(XtextAbstractTokenWithCardinality.class);
  }

  @Override
  @Nullable
  public XtextAction getAction() {
    return findChildByClass(XtextAction.class);
  }

}
