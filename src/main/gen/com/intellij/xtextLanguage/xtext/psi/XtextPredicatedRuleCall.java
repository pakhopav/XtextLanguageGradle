// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextPredicatedRuleCall extends PsiElement {

  @NotNull
  List<XtextNamedArgument> getNamedArgumentList();

  @NotNull
  XtextREFERENCEAbstractRuleRuleID getREFERENCEAbstractRuleRuleID();

  @Nullable
  PsiElement getLAngleBracketKeyword();

  @Nullable
  PsiElement getPredKeyword();

  @Nullable
  PsiElement getRAngleBracketKeyword();

  @Nullable
  PsiElement getWeakPredKeyword();

}
