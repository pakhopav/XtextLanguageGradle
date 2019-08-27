// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XtextAction extends PsiElement {

  @NotNull
  XtextTypeRef getTypeRef();

  @Nullable
  XtextValidID getValidID();

  @Nullable
  PsiElement getCurrent();

  @Nullable
  PsiElement getDot();

  @Nullable
  PsiElement getEquals();

  @NotNull
  PsiElement getLBrace();

  @Nullable
  PsiElement getPlusEquals();

  @NotNull
  PsiElement getRBrace();

}
