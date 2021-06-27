// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.*;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextAbstractTerminalImpl extends XtextPsiCompositeElementImpl implements XtextAbstractTerminal {

  public XtextAbstractTerminalImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAbstractTerminal(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @Nullable
  public XtextKeyword getKeyword() {
    return findChildByClass(XtextKeyword.class);
  }

  @Override
  @Nullable
  public XtextParenthesizedElement getParenthesizedElement() {
    return findChildByClass(XtextParenthesizedElement.class);
  }

  @Override
  @Nullable
  public XtextPredicatedGroup getPredicatedGroup() {
    return findChildByClass(XtextPredicatedGroup.class);
  }

  @Override
  @Nullable
  public XtextPredicatedKeyword getPredicatedKeyword() {
    return findChildByClass(XtextPredicatedKeyword.class);
  }

  @Override
  @Nullable
  public XtextPredicatedRuleCall getPredicatedRuleCall() {
    return findChildByClass(XtextPredicatedRuleCall.class);
  }

  @Override
  @Nullable
  public XtextRuleCall getRuleCall() {
    return findChildByClass(XtextRuleCall.class);
  }

}
