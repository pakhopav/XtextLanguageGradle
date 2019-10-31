// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface XtextPredicatedGroup extends PsiElement {

  @NotNull
  XtextAlternatives getAlternatives();

  @NotNull
  PsiElement getLBracketKeyword();

  @Nullable
  PsiElement getPredKeyword();

  @NotNull
  PsiElement getRBracketKeyword();

  @Nullable
  PsiElement getWeakPredKeyword();

}
