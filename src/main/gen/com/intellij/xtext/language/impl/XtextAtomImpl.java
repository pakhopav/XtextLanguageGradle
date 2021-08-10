// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.*;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextAtomImpl extends XtextPsiCompositeElementImpl implements XtextAtom {

  public XtextAtomImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAtom(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @Nullable
  public XtextLiteralCondition getLiteralCondition() {
    return findChildByClass(XtextLiteralCondition.class);
  }

  @Override
  @Nullable
  public XtextParameterReference getParameterReference() {
    return findChildByClass(XtextParameterReference.class);
  }

  @Override
  @Nullable
  public XtextParenthesizedCondition getParenthesizedCondition() {
    return findChildByClass(XtextParenthesizedCondition.class);
  }

}
