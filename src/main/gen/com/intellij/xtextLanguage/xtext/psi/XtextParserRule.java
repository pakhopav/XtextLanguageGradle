// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XtextParserRule extends XtextAbstractRule {

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
  PsiElement getAsterisk();

  @NotNull
  PsiElement getColon();

  @Nullable
  PsiElement getFragment();

  @Nullable
  PsiElement getHidden();

  @Nullable
  PsiElement getLBracket();

  @Nullable
  PsiElement getReturns();

  @Nullable
  PsiElement getRBracket();

  @NotNull
  PsiElement getSemicolon();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

}
