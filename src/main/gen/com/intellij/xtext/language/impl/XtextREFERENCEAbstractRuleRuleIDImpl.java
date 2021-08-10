// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextREFERENCEAbstractRuleRuleID;
import com.intellij.xtext.language.psi.XtextRuleID;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

public class XtextREFERENCEAbstractRuleRuleIDImpl extends XtextPsiCompositeElementImpl implements XtextREFERENCEAbstractRuleRuleID {

  public XtextREFERENCEAbstractRuleRuleIDImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitREFERENCEAbstractRuleRuleID(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @NotNull
  public XtextRuleID getRuleID() {
    return findNotNullChildByClass(XtextRuleID.class);
  }

}
