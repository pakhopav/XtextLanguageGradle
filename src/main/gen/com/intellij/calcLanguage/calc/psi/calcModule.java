// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface calcModule extends PsiNameIdentifierOwner {

  @NotNull
  List<calcImport> getImportList();

  @NotNull
  List<calcStatement> getStatementList();

  @NotNull
  PsiElement getId();

  @NotNull
  PsiElement getModuleKeyword();

  String getName();

  PsiElement setName(String newName);

  PsiElement getNameIdentifier();

}
