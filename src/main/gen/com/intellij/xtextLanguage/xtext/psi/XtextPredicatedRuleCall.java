// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XtextPredicatedRuleCall extends PsiElement {

  @NotNull
  List<XtextNamedArgument> getNamedArgumentList();

  @NotNull
  XtextREFERENCEAbstractRuleRuleID getREFERENCEAbstractRuleRuleID();

  @Nullable
  PsiElement getLAngleBracket();

  @Nullable
  PsiElement getPred();

  @Nullable
  PsiElement getRAngleBracket();

  @Nullable
  PsiElement getWeakPred();

}
