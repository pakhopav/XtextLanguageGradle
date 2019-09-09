// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextTerminalRule extends PsiElement {

  @NotNull
  List<XtextAnnotation> getAnnotationList();

  @Nullable
  XtextTerminalAlternatives getTerminalAlternatives();

  @Nullable
  XtextTypeRef getTypeRef();

  @Nullable
  XtextValidID getValidID();

  @Nullable
  PsiElement getColon();

  @Nullable
  PsiElement getFragment();

  @Nullable
  PsiElement getReturns();

    @NotNull
  PsiElement getSemicolon();

  @NotNull
  PsiElement getTerminal();

}
