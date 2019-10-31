// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEAbstractMetamodelDeclaration;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEEcoreEClassifier;
import com.intellij.xtextLanguage.xtext.psi.XtextTypeRef;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.COLONS_KEYWORD;

public class XtextTypeRefImpl extends XtextPsiCompositeElementImpl implements XtextTypeRef {

  public XtextTypeRefImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitTypeRef(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public XtextREFERENCEAbstractMetamodelDeclaration getREFERENCEAbstractMetamodelDeclaration() {
    return findChildByClass(XtextREFERENCEAbstractMetamodelDeclaration.class);
  }

  @Override
  @NotNull
  public XtextREFERENCEEcoreEClassifier getREFERENCEEcoreEClassifier() {
    return findNotNullChildByClass(XtextREFERENCEEcoreEClassifier.class);
  }

  @Override
  @Nullable
  public PsiElement getColonsKeyword() {
      return findChildByType(COLONS_KEYWORD);
  }

}
