// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface calcDefinition extends PsiElement {

  @NotNull
  List<calcDeclaredParameter> getDeclaredParameterList();

  @NotNull
  calcExpression getExpression();

  @NotNull
  PsiElement getColonKeyword();

  @NotNull
  PsiElement getDefKeyword();

  @NotNull
  PsiElement getId();

  @Nullable
  PsiElement getLBracketKeyword();

  @Nullable
  PsiElement getRBracketKeyword();

  @NotNull
  PsiElement getSemicolonKeyword();

}
