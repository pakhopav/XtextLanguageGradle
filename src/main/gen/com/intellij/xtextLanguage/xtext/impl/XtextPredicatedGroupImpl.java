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
  public PsiElement getLBracket() {
    return findNotNullChildByType(L_BRACKET);
  }

  @Override
  @Nullable
  public PsiElement getPred() {
    return findChildByType(PRED);
  }

  @Override
  @NotNull
  public PsiElement getRBracket() {
    return findNotNullChildByType(R_BRACKET);
  }

  @Override
  @Nullable
  public PsiElement getWeakPred() {
    return findChildByType(WEAK_PRED);
  }

}
