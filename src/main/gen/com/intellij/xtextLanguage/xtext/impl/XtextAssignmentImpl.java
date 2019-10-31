// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAssignableTerminal;
import com.intellij.xtextLanguage.xtext.psi.XtextAssignment;
import com.intellij.xtextLanguage.xtext.psi.XtextValidID;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*;

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
  public PsiElement getEqualsKeyword() {
      return findChildByType(EQUALS_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getPlusEqualsKeyword() {
      return findChildByType(PLUS_EQUALS_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getPredKeyword() {
      return findChildByType(PRED_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getQuesEqualsKeyword() {
      return findChildByType(QUES_EQUALS_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getWeakPredKeyword() {
      return findChildByType(WEAK_PRED_KEYWORD);
  }

}
