// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAlternatives;
import com.intellij.xtextLanguage.xtext.psi.XtextParenthesizedElement;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.L_BRACKET_KEYWORD;
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.R_BRACKET_KEYWORD;

public class XtextParenthesizedElementImpl extends XtextPsiCompositeElementImpl implements XtextParenthesizedElement {

  public XtextParenthesizedElementImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitParenthesizedElement(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XtextAlternatives getAlternatives() {
    return findNotNullChildByClass(XtextAlternatives.class);
  }

  @Override
  @NotNull
  public PsiElement getLBracketKeyword() {
      return findNotNullChildByType(L_BRACKET_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getRBracketKeyword() {
      return findNotNullChildByType(R_BRACKET_KEYWORD);
  }

}
