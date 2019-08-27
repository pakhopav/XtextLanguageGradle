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
  public PsiElement getAs() {
    return findChildByType(AS);
  }

  @Override
  @NotNull
  public PsiElement getGenerate() {
    return findNotNullChildByType(GENERATE);
  }

}
