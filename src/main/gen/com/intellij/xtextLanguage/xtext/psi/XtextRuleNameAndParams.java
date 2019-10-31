// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextRuleNameAndParams extends PsiElement {

  @NotNull
  List<XtextParameter> getParameterList();

  @NotNull
  XtextValidID getValidID();

  @Nullable
  PsiElement getLAngleBracketKeyword();

  @Nullable
  PsiElement getRAngleBracketKeyword();

}
