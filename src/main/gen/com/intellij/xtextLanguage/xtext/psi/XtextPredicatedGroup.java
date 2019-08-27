// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XtextPredicatedGroup extends PsiElement {

  @NotNull
  XtextAlternatives getAlternatives();

  @NotNull
  PsiElement getLBracket();

  @Nullable
  PsiElement getPred();

  @NotNull
  PsiElement getRBracket();

  @Nullable
  PsiElement getWeakPred();

}
