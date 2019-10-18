// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextParameterReference;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEParameterID;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

public class XtextParameterReferenceImpl extends XtextPsiCompositeElementImpl implements XtextParameterReference {

  public XtextParameterReferenceImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitParameterReference(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XtextREFERENCEParameterID getREFERENCEParameterID() {
      return findNotNullChildByClass(XtextREFERENCEParameterID.class);
  }

}
