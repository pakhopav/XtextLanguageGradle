// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.*;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextAssignableTerminalImpl extends XtextPsiCompositeElementImpl implements XtextAssignableTerminal {

  public XtextAssignableTerminalImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAssignableTerminal(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @Nullable
  public XtextCrossReference getCrossReference() {
    return findChildByClass(XtextCrossReference.class);
  }

  @Override
  @Nullable
  public XtextKeyword getKeyword() {
    return findChildByClass(XtextKeyword.class);
  }

  @Override
  @Nullable
  public XtextParenthesizedAssignableElement getParenthesizedAssignableElement() {
    return findChildByClass(XtextParenthesizedAssignableElement.class);
  }

  @Override
  @Nullable
  public XtextRuleCall getRuleCall() {
    return findChildByClass(XtextRuleCall.class);
  }

}
