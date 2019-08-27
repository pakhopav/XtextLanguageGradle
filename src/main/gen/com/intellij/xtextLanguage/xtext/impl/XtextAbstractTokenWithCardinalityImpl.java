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

public class XtextAbstractTokenWithCardinalityImpl extends XtextPsiCompositeElementImpl implements XtextAbstractTokenWithCardinality {

  public XtextAbstractTokenWithCardinalityImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAbstractTokenWithCardinality(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public XtextAbstractTerminal getAbstractTerminal() {
    return findChildByClass(XtextAbstractTerminal.class);
  }

  @Override
  @Nullable
  public XtextAssignment getAssignment() {
    return findChildByClass(XtextAssignment.class);
  }

  @Override
  @Nullable
  public PsiElement getAsterisk() {
    return findChildByType(ASTERISK);
  }

  @Override
  @Nullable
  public PsiElement getPlus() {
    return findChildByType(PLUS);
  }

  @Override
  @Nullable
  public PsiElement getQuesMark() {
    return findChildByType(QUES_MARK);
  }

}
