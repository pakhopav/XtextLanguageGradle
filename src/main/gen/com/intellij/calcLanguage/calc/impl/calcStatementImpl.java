// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.calcDefinition;
import com.intellij.calcLanguage.calc.psi.calcEvaluation;
import com.intellij.calcLanguage.calc.psi.calcStatement;
import com.intellij.calcLanguage.calc.psi.calcVisitor;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiCompositeElementImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class calcStatementImpl extends calcPsiCompositeElementImpl implements calcStatement {

  public calcStatementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitStatement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public calcDefinition getDefinition() {
    return findChildByClass(calcDefinition.class);
  }

  @Override
  @Nullable
  public calcEvaluation getEvaluation() {
    return findChildByClass(calcEvaluation.class);
  }

}
