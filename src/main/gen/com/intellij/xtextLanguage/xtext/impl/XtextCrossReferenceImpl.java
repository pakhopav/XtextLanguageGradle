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

public class XtextCrossReferenceImpl extends XtextPsiCompositeElementImpl implements XtextCrossReference {

  public XtextCrossReferenceImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitCrossReference(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public XtextCrossReferenceableTerminal getCrossReferenceableTerminal() {
    return findChildByClass(XtextCrossReferenceableTerminal.class);
  }

  @Override
  @NotNull
  public XtextTypeRef getTypeRef() {
    return findNotNullChildByClass(XtextTypeRef.class);
  }

  @Override
  @NotNull
  public PsiElement getLSquareBracket() {
    return findNotNullChildByType(L_SQUARE_BRACKET);
  }

  @Override
  @Nullable
  public PsiElement getPipe() {
    return findChildByType(PIPE);
  }

  @Override
  @NotNull
  public PsiElement getRSquareBracket() {
    return findNotNullChildByType(R_SQUARE_BRACKET);
  }

}
