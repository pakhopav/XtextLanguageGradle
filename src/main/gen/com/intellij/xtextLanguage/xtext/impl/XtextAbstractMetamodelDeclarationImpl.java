// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractMetamodelDeclaration;
import com.intellij.xtextLanguage.xtext.psi.XtextGeneratedMetamodel;
import com.intellij.xtextLanguage.xtext.psi.XtextReferencedMetamodel;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextNamedElementImpl;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextAbstractMetamodelDeclarationImpl extends XtextNamedElementImpl implements XtextAbstractMetamodelDeclaration {

  public XtextAbstractMetamodelDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAbstractMetamodelDeclaration(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public XtextGeneratedMetamodel getGeneratedMetamodel() {
    return findChildByClass(XtextGeneratedMetamodel.class);
  }

  @Override
  @Nullable
  public XtextReferencedMetamodel getReferencedMetamodel() {
    return findChildByClass(XtextReferencedMetamodel.class);
  }

    @Override
    public String getName() {
        return XtextPsiImplUtil.getName(this);
    }

    @Override
    public PsiElement setName(String newName) {
        return XtextPsiImplUtil.setName(this, newName);
    }

    @Override
    public PsiElement getNameIdentifier() {
        return XtextPsiImplUtil.getNameIdentifier(this);
    }

}
