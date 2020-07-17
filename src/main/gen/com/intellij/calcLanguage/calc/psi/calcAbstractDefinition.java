// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.Nullable;

public interface calcAbstractDefinition extends PsiNameIdentifierOwner {

  @Nullable
  calcDeclaredParameter getDeclaredParameter();

  @Nullable
  calcDefinition getDefinition();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

}
