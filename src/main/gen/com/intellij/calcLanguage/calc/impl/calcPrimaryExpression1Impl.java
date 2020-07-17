// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.calcExpression;
import com.intellij.calcLanguage.calc.psi.calcPrimaryExpression1;
import com.intellij.calcLanguage.calc.psi.calcVisitor;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiCompositeElementImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import static com.intellij.calcLanguage.calc.psi.calcTypes.L_BRACKET_KEYWORD;
import static com.intellij.calcLanguage.calc.psi.calcTypes.R_BRACKET_KEYWORD;

public class calcPrimaryExpression1Impl extends calcPsiCompositeElementImpl implements calcPrimaryExpression1 {

  public calcPrimaryExpression1Impl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitPrimaryExpression1(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public calcExpression getExpression() {
    return findNotNullChildByClass(calcExpression.class);
  }

  @Override
  @NotNull
  public PsiElement getLBracketKeyword() {
    return findNotNullChildByType(L_BRACKET_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getRBracketKeyword() {
    return findNotNullChildByType(R_BRACKET_KEYWORD);
  }

}
