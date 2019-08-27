// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XtextRuleNameAndParams extends PsiElement {

  @NotNull
  List<XtextParameter> getParameterList();

  @NotNull
  XtextValidID getValidID();

  @Nullable
  PsiElement getLAngleBracket();

  @Nullable
  PsiElement getRAngleBracket();

}
