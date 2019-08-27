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

public class XtextNegationImpl extends XtextPsiCompositeElementImpl implements XtextNegation {

  public XtextNegationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitNegation(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public XtextAtom getAtom() {
    return findChildByClass(XtextAtom.class);
  }

  @Override
  @Nullable
  public XtextNegation getNegation() {
    return findChildByClass(XtextNegation.class);
  }

  @Override
  @Nullable
  public PsiElement getAcxMark() {
    return findChildByType(ACX_MARK);
  }

}
