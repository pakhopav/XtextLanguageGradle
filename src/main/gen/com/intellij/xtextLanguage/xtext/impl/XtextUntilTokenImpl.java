// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextTerminalTokenElement;
import com.intellij.xtextLanguage.xtext.psi.XtextUntilToken;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.WEAK_PRED_KEYWORD;

public class XtextUntilTokenImpl extends XtextPsiCompositeElementImpl implements XtextUntilToken {

  public XtextUntilTokenImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitUntilToken(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @NotNull
  public XtextTerminalTokenElement getTerminalTokenElement() {
    return findNotNullChildByClass(XtextTerminalTokenElement.class);
  }

  @Override
  @NotNull
  public PsiElement getWeakPredKeyword() {
      return findNotNullChildByType(WEAK_PRED_KEYWORD);
  }

}
