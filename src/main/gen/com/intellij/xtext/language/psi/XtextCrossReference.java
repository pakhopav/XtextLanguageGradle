// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface XtextCrossReference extends PsiElement {

  @Nullable
  XtextCrossReferenceableTerminal getCrossReferenceableTerminal();

  @NotNull
  XtextTypeRef getTypeRef();

  @NotNull
  PsiElement getLSquareBracketKeyword();

  @Nullable
  PsiElement getPipeKeyword();

  @NotNull
  PsiElement getRSquareBracketKeyword();

}
