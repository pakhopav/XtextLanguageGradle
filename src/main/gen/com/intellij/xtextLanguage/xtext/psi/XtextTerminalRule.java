// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.psi.PsiElement;

public interface XtextTerminalRule extends XtextAbstractRule {

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

  @Nullable
  PsiElement getSemicolon();

  @NotNull
  PsiElement getTerminal();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

}
