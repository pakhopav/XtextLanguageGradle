// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XtextConditionalBranch extends PsiElement {

  @NotNull
  List<XtextAbstractToken> getAbstractTokenList();

  @Nullable
  XtextDisjunction getDisjunction();

  @Nullable
  XtextUnorderedGroup getUnorderedGroup();

  @Nullable
  PsiElement getLAngleBracket();

  @Nullable
  PsiElement getRAngleBracket();

}
