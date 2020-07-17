// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.calcExpression;
import com.intellij.calcLanguage.calc.psi.calcPrimaryExpression3;
import com.intellij.calcLanguage.calc.psi.calcREFERENCEAbstractDefinitionID;
import com.intellij.calcLanguage.calc.psi.calcVisitor;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiCompositeElementImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.calcLanguage.calc.psi.calcTypes.L_BRACKET_KEYWORD;
import static com.intellij.calcLanguage.calc.psi.calcTypes.R_BRACKET_KEYWORD;

public class calcPrimaryExpression3Impl extends calcPsiCompositeElementImpl implements calcPrimaryExpression3 {

  public calcPrimaryExpression3Impl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitPrimaryExpression3(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<calcExpression> getExpressionList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, calcExpression.class);
  }

  @Override
  @NotNull
  public calcREFERENCEAbstractDefinitionID getREFERENCEAbstractDefinitionID() {
    return findNotNullChildByClass(calcREFERENCEAbstractDefinitionID.class);
  }

  @Override
  @Nullable
  public PsiElement getLBracketKeyword() {
    return findChildByType(L_BRACKET_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getRBracketKeyword() {
    return findChildByType(R_BRACKET_KEYWORD);
  }

}
