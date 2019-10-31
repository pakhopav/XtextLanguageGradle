// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextGeneratedMetamodel;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEEcoreEPackageSTRING;
import com.intellij.xtextLanguage.xtext.psi.XtextValidID;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.AS_KEYWORD;
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.GENERATE_KEYWORD;

public class XtextGeneratedMetamodelImpl extends XtextPsiCompositeElementImpl implements XtextGeneratedMetamodel {

  public XtextGeneratedMetamodelImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitGeneratedMetamodel(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XtextREFERENCEEcoreEPackageSTRING getREFERENCEEcoreEPackageSTRING() {
    return findNotNullChildByClass(XtextREFERENCEEcoreEPackageSTRING.class);
  }

  @Override
  @NotNull
  public List<XtextValidID> getValidIDList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextValidID.class);
  }

  @Override
  @Nullable
  public PsiElement getAsKeyword() {
      return findChildByType(AS_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getGenerateKeyword() {
      return findNotNullChildByType(GENERATE_KEYWORD);
  }

}
