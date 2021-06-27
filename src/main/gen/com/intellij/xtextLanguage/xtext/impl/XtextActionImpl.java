// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAction;
import com.intellij.xtextLanguage.xtext.psi.XtextTypeRef;
import com.intellij.xtextLanguage.xtext.psi.XtextValidID;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*;

public class XtextActionImpl extends XtextPsiCompositeElementImpl implements XtextAction {

  public XtextActionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAction(this);
  }

    @Override
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
  public PsiElement getCurrentKeyword() {
      return findChildByType(CURRENT_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getDotKeyword() {
      return findChildByType(DOT_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getEqualsKeyword() {
      return findChildByType(EQUALS_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getLBraceKeyword() {
      return findNotNullChildByType(L_BRACE_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getPlusEqualsKeyword() {
      return findChildByType(PLUS_EQUALS_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getRBraceKeyword() {
      return findNotNullChildByType(R_BRACE_KEYWORD);
  }

}
