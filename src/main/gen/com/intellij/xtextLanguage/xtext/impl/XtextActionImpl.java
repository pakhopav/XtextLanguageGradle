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

public class XtextActionImpl extends XtextPsiCompositeElementImpl implements XtextAction {

  public XtextActionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAction(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XtextTypeRef getTypeRef() {
    return findNotNullChildByClass(XtextTypeRef.class);
  }

  @Override
  @Nullable
  public XtextValidID getValidID() {
    return findChildByClass(XtextValidID.class);
  }

  @Override
  @Nullable
  public PsiElement getCurrent() {
    return findChildByType(CURRENT);
  }

  @Override
  @Nullable
  public PsiElement getDot() {
    return findChildByType(DOT);
  }

  @Override
  @Nullable
  public PsiElement getEquals() {
    return findChildByType(EQUALS);
  }

  @Override
  @NotNull
  public PsiElement getLBrace() {
    return findNotNullChildByType(L_BRACE);
  }

  @Override
  @Nullable
  public PsiElement getPlusEquals() {
    return findChildByType(PLUS_EQUALS);
  }

  @Override
  @NotNull
  public PsiElement getRBrace() {
    return findNotNullChildByType(R_BRACE);
  }

}
