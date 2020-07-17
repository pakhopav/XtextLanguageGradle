// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.calcPrimaryExpression2;
import com.intellij.calcLanguage.calc.psi.calcVisitor;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiCompositeElementImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import static com.intellij.calcLanguage.calc.psi.calcTypes.NUMBER;

public class calcPrimaryExpression2Impl extends calcPsiCompositeElementImpl implements calcPrimaryExpression2 {

  public calcPrimaryExpression2Impl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitPrimaryExpression2(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getNumber() {
    return findNotNullChildByType(NUMBER);
  }

}
