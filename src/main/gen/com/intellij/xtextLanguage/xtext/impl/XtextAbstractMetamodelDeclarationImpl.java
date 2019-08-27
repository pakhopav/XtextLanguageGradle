// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import com.intellij.xtextLanguage.xtext.psi.*;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiImplUtil;

public class XtextAbstractMetamodelDeclarationImpl extends XtextPsiCompositeElementImpl implements XtextAbstractMetamodelDeclaration {

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

}
