// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextRuleCall extends PsiElement {

  @NotNull
  List<XtextNamedArgument> getNamedArgumentList();

  @NotNull
  XtextREFERENCEAbstractRuleRuleID getREFERENCEAbstractRuleRuleID();

  @Nullable
  PsiElement getLAngleBracketKeyword();

  @Nullable
  PsiElement getRAngleBracketKeyword();

}
