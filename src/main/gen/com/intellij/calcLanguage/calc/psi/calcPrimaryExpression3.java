// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface calcPrimaryExpression3 extends PsiElement {

  @NotNull
  List<calcExpression> getExpressionList();

  @NotNull
  calcREFERENCEAbstractDefinitionID getREFERENCEAbstractDefinitionID();

  @Nullable
  PsiElement getLBracketKeyword();

  @Nullable
  PsiElement getRBracketKeyword();

}
