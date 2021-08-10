// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextAssignableTerminal;
import com.intellij.xtext.language.psi.XtextAssignment;
import com.intellij.xtext.language.psi.XtextValidID;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtext.language.psi.XtextTypes.*;

public class XtextAssignmentImpl extends XtextPsiCompositeElementImpl implements XtextAssignment {

  public XtextAssignmentImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitAssignment(this);
  }

    @Override
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
