// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAtom;
import com.intellij.xtextLanguage.xtext.psi.XtextNegation;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleFromNegationNegation;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextNegationImpl extends XtextPsiCompositeElementImpl implements XtextNegation {

  public XtextNegationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitNegation(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public XtextAtom getAtom() {
    return findChildByClass(XtextAtom.class);
  }

  @Override
  @Nullable
  public XtextRuleFromNegationNegation getRuleFromNegationNegation() {
      return findChildByClass(XtextRuleFromNegationNegation.class);
  }

}
