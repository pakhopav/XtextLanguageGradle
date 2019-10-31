// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextDisjunction;
import com.intellij.xtextLanguage.xtext.psi.XtextNamedArgument;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEParameterID;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.EQUALS_KEYWORD;

public class XtextNamedArgumentImpl extends XtextPsiCompositeElementImpl implements XtextNamedArgument {

  public XtextNamedArgumentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitNamedArgument(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XtextDisjunction getDisjunction() {
    return findNotNullChildByClass(XtextDisjunction.class);
  }

  @Override
  @Nullable
  public XtextREFERENCEParameterID getREFERENCEParameterID() {
    return findChildByClass(XtextREFERENCEParameterID.class);
  }

  @Override
  @Nullable
  public PsiElement getEqualsKeyword() {
      return findChildByType(EQUALS_KEYWORD);
  }

}
