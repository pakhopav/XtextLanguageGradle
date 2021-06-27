// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextCrossReferenceableTerminal;
import com.intellij.xtextLanguage.xtext.psi.XtextKeyword;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleCall;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextCrossReferenceableTerminalImpl extends XtextPsiCompositeElementImpl implements XtextCrossReferenceableTerminal {

  public XtextCrossReferenceableTerminalImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitCrossReferenceableTerminal(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @Nullable
  public XtextKeyword getKeyword() {
    return findChildByClass(XtextKeyword.class);
  }

  @Override
  @Nullable
  public XtextRuleCall getRuleCall() {
    return findChildByClass(XtextRuleCall.class);
  }

}
