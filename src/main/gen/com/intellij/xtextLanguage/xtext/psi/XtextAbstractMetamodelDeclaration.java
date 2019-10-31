// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.Nullable;

public interface XtextAbstractMetamodelDeclaration extends PsiNameIdentifierOwner {

  @Nullable
  XtextGeneratedMetamodel getGeneratedMetamodel();

  @Nullable
  XtextReferencedMetamodel getReferencedMetamodel();

    String getName();

    PsiElement setName(String newName);

    PsiElement getNameIdentifier();

}
