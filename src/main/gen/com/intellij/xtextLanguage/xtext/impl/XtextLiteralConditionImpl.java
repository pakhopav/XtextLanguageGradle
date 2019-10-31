// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextLiteralCondition;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleFromLiteralConditionBranch1;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

public class XtextLiteralConditionImpl extends XtextPsiCompositeElementImpl implements XtextLiteralCondition {

  public XtextLiteralConditionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitLiteralCondition(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XtextRuleFromLiteralConditionBranch1 getRuleFromLiteralConditionBranch1() {
      return findNotNullChildByClass(XtextRuleFromLiteralConditionBranch1.class);
  }

}
