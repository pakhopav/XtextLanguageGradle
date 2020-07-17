// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.*;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiCompositeElementImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class calcPrimaryExpressionImpl extends calcPsiCompositeElementImpl implements calcPrimaryExpression {

  public calcPrimaryExpressionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitPrimaryExpression(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public calcPrimaryExpression1 getPrimaryExpression1() {
    return findChildByClass(calcPrimaryExpression1.class);
  }

  @Override
  @Nullable
  public calcPrimaryExpression2 getPrimaryExpression2() {
    return findChildByClass(calcPrimaryExpression2.class);
  }

  @Override
  @Nullable
  public calcPrimaryExpression3 getPrimaryExpression3() {
    return findChildByClass(calcPrimaryExpression3.class);
  }

}
