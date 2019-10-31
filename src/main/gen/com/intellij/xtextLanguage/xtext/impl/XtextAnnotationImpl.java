// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAnnotation;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.AT_SIGN_KEYWORD;
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.ID;

public class XtextAnnotationImpl extends XtextPsiCompositeElementImpl implements XtextAnnotation {

  public XtextAnnotationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAnnotation(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getAtSignKeyword() {
      return findNotNullChildByType(AT_SIGN_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getId() {
    return findNotNullChildByType(ID);
  }

}
