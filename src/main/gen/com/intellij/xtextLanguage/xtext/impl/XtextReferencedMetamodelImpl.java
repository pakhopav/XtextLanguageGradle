// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEEPackageSTRING;
import com.intellij.xtextLanguage.xtext.psi.XtextReferencedMetamodel;
import com.intellij.xtextLanguage.xtext.psi.XtextValidID;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.AS_KEYWORD;
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.IMPORT_KEYWORD;

public class XtextReferencedMetamodelImpl extends XtextAbstractMetamodelDeclarationImpl implements XtextReferencedMetamodel {

    public XtextReferencedMetamodelImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitReferencedMetamodel(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public XtextREFERENCEEPackageSTRING getREFERENCEEPackageSTRING() {
        return findNotNullChildByClass(XtextREFERENCEEPackageSTRING.class);
    }

  @Override
  @Nullable
  public XtextValidID getValidID() {
    return findChildByClass(XtextValidID.class);
  }

  @Override
  @Nullable
  public PsiElement getAsKeyword() {
      return findChildByType(AS_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getImportKeyword() {
      return findNotNullChildByType(IMPORT_KEYWORD);
  }

}
