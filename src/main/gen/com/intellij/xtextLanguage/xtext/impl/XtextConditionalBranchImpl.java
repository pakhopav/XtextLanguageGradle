// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextConditionalBranch;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleFromConditionalBranchGroup;
import com.intellij.xtextLanguage.xtext.psi.XtextUnorderedGroup;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextConditionalBranchImpl extends XtextPsiCompositeElementImpl implements XtextConditionalBranch {

  public XtextConditionalBranchImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitConditionalBranch(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public XtextRuleFromConditionalBranchGroup getRuleFromConditionalBranchGroup() {
      return findChildByClass(XtextRuleFromConditionalBranchGroup.class);
  }

  @Override
  @Nullable
  public XtextUnorderedGroup getUnorderedGroup() {
    return findChildByClass(XtextUnorderedGroup.class);
  }

}
