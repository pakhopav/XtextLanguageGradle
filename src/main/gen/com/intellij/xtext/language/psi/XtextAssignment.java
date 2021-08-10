// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface XtextAssignment extends PsiElement {

  @NotNull
  XtextAssignableTerminal getAssignableTerminal();

  @NotNull
  XtextValidID getValidID();

  @Nullable
  PsiElement getEqualsKeyword();

  @Nullable
  PsiElement getPlusEqualsKeyword();

  @Nullable
  PsiElement getPredKeyword();

  @Nullable
  PsiElement getQuesEqualsKeyword();

  @Nullable
  PsiElement getWeakPredKeyword();

}
