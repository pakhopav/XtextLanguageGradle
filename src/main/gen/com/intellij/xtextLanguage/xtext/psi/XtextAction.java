// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface XtextAction extends PsiElement {

  @NotNull
  XtextTypeRef getTypeRef();

  @Nullable
  XtextValidID getValidID();

  @Nullable
  PsiElement getCurrentKeyword();

  @Nullable
  PsiElement getDotKeyword();

  @Nullable
  PsiElement getEqualsKeyword();

  @NotNull
  PsiElement getLBraceKeyword();

  @Nullable
  PsiElement getPlusEqualsKeyword();

  @NotNull
  PsiElement getRBraceKeyword();

}
