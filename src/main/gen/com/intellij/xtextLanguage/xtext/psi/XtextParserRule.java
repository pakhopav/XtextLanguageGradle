// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextParserRule extends PsiElement {

    @NotNull
  XtextAlternatives getAlternatives();

  @NotNull
  List<XtextAnnotation> getAnnotationList();

  @NotNull
  List<XtextREFERENCEAbstractRuleRuleID> getREFERENCEAbstractRuleRuleIDList();

  @NotNull
  XtextRuleNameAndParams getRuleNameAndParams();

  @Nullable
  XtextTypeRef getTypeRef();

  @Nullable
  PsiElement getAsteriskKeyword();

    @NotNull
    PsiElement getColonKeyword();

  @Nullable
  PsiElement getFragmentKeyword();

  @Nullable
  PsiElement getHiddenKeyword();

  @Nullable
  PsiElement getLBracketKeyword();

  @Nullable
  PsiElement getReturnsKeyword();

  @Nullable
  PsiElement getRBracketKeyword();

  @NotNull
  PsiElement getSemicolonKeyword();

}
