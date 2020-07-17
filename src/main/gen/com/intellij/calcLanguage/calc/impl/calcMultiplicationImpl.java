// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.calcMultiplication;
import com.intellij.calcLanguage.calc.psi.calcPrimaryExpression;
import com.intellij.calcLanguage.calc.psi.calcVisitor;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiCompositeElementImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class calcMultiplicationImpl extends calcPsiCompositeElementImpl implements calcMultiplication {

  public calcMultiplicationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitMultiplication(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<calcPrimaryExpression> getPrimaryExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, calcPrimaryExpression.class);
  }

}
