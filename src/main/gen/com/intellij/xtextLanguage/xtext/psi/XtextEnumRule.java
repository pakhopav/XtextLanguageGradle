// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextEnumRule extends PsiElement {

  @NotNull
  List<XtextAnnotation> getAnnotationList();

    @NotNull
  XtextEnumLiterals getEnumLiterals();

  @Nullable
  XtextTypeRef getTypeRef();

    @NotNull
  XtextValidID getValidID();

    @NotNull
    PsiElement getColonKeyword();

  @NotNull
  PsiElement getEnumKeyword();

  @Nullable
  PsiElement getReturnsKeyword();

  @NotNull
  PsiElement getSemicolonKeyword();

}
