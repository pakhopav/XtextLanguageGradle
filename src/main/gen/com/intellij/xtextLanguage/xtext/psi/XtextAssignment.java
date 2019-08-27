// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XtextAssignment extends PsiElement {

  @NotNull
  XtextAssignableTerminal getAssignableTerminal();

  @NotNull
  XtextValidID getValidID();

  @Nullable
  PsiElement getEquals();

  @Nullable
  PsiElement getPlusEquals();

  @Nullable
  PsiElement getPred();

  @Nullable
  PsiElement getQuesEquals();

  @Nullable
  PsiElement getWeakPred();

}
