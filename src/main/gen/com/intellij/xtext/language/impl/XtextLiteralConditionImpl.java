// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextLiteralCondition;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtext.language.psi.XtextTypes.FALSE_KEYWORD;
import static com.intellij.xtext.language.psi.XtextTypes.TRUE_KEYWORD;

public class XtextLiteralConditionImpl extends XtextPsiCompositeElementImpl implements XtextLiteralCondition {

  public XtextLiteralConditionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
      visitor.visitLiteralCondition(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public PsiElement getFalseKeyword() {
        return findChildByType(FALSE_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getTrueKeyword() {
        return findChildByType(TRUE_KEYWORD);
    }

}
