// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextParserRule extends XtextAbstractRule {

  @NotNull
  XtextAlternatives getAlternatives();

  @NotNull
  List<XtextAnnotation> getAnnotationList();

  @NotNull
  List<XtextParameter> getParameterList();

  @NotNull
  List<XtextREFERENCEAbstractRuleRuleID> getREFERENCEAbstractRuleRuleIDList();

  @Nullable
  XtextTypeRef getTypeRef();

  @NotNull
  XtextValidID getValidID();

  @Nullable
  PsiElement getAsteriskKeyword();

  @NotNull
  PsiElement getColonKeyword();

  @Nullable
  PsiElement getFragmentKeyword();

  @Nullable
  PsiElement getHiddenKeyword();

  @Nullable
  PsiElement getLAngleBracketKeyword();

  @Nullable
  PsiElement getLBracketKeyword();

  @Nullable
  PsiElement getReturnsKeyword();

  @Nullable
  PsiElement getRAngleBracketKeyword();

  @Nullable
  PsiElement getRBracketKeyword();

  @NotNull
  PsiElement getSemicolonKeyword();

}
