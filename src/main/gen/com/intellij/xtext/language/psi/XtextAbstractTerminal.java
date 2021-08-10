// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface XtextAbstractTerminal extends PsiElement {

  @Nullable
  XtextKeyword getKeyword();

  @Nullable
  XtextParenthesizedElement getParenthesizedElement();

  @Nullable
  XtextPredicatedGroup getPredicatedGroup();

  @Nullable
  XtextPredicatedKeyword getPredicatedKeyword();

  @Nullable
  XtextPredicatedRuleCall getPredicatedRuleCall();

  @Nullable
  XtextRuleCall getRuleCall();

}
