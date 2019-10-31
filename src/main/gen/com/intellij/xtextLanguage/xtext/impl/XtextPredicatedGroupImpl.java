// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAlternatives;
import com.intellij.xtextLanguage.xtext.psi.XtextPredicatedGroup;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*;

public class XtextPredicatedGroupImpl extends XtextPsiCompositeElementImpl implements XtextPredicatedGroup {

  public XtextPredicatedGroupImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitPredicatedGroup(this);
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
  @Nullable
  public PsiElement getPredKeyword() {
      return findChildByType(PRED_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getRBracketKeyword() {
      return findNotNullChildByType(R_BRACKET_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getWeakPredKeyword() {
      return findChildByType(WEAK_PRED_KEYWORD);
  }

}
