// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextEnumRule extends XtextAbstractRule {

  @NotNull
  List<XtextAnnotation> getAnnotationList();

    @Nullable
  XtextEnumLiterals getEnumLiterals();

  @Nullable
  XtextTypeRef getTypeRef();

    @Nullable
  XtextValidID getValidID();

    @Nullable
  PsiElement getColon();

  @NotNull
  PsiElement getEnum();

  @Nullable
  PsiElement getReturns();

  @NotNull
  PsiElement getSemicolon();

    String getName();

    PsiElement setName(String newName);

    PsiElement getNameIdentifier();

}
