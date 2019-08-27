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

public class XtextAssignmentImpl extends XtextPsiCompositeElementImpl implements XtextAssignment {

  public XtextAssignmentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAssignment(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XtextAssignableTerminal getAssignableTerminal() {
    return findNotNullChildByClass(XtextAssignableTerminal.class);
  }

  @Override
  @NotNull
  public XtextValidID getValidID() {
    return findNotNullChildByClass(XtextValidID.class);
  }

  @Override
  @Nullable
  public PsiElement getEquals() {
    return findChildByType(EQUALS);
  }

  @Override
  @Nullable
  public PsiElement getPlusEquals() {
    return findChildByType(PLUS_EQUALS);
  }

  @Override
  @Nullable
  public PsiElement getPred() {
    return findChildByType(PRED);
  }

  @Override
  @Nullable
  public PsiElement getQuesEquals() {
    return findChildByType(QUES_EQUALS);
  }

  @Override
  @Nullable
  public PsiElement getWeakPred() {
    return findChildByType(WEAK_PRED);
  }

}
