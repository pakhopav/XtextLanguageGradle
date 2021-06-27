// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextNamedArgument;
import com.intellij.xtextLanguage.xtext.psi.XtextPredicatedRuleCall;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEAbstractRuleRuleID;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*;

public class XtextPredicatedRuleCallImpl extends XtextPsiCompositeElementImpl implements XtextPredicatedRuleCall {

  public XtextPredicatedRuleCallImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitPredicatedRuleCall(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @NotNull
  public List<XtextNamedArgument> getNamedArgumentList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextNamedArgument.class);
  }

  @Override
  @NotNull
  public XtextREFERENCEAbstractRuleRuleID getREFERENCEAbstractRuleRuleID() {
    return findNotNullChildByClass(XtextREFERENCEAbstractRuleRuleID.class);
  }

  @Override
  @Nullable
  public PsiElement getLAngleBracketKeyword() {
      return findChildByType(L_ANGLE_BRACKET_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getPredKeyword() {
      return findChildByType(PRED_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getRAngleBracketKeyword() {
      return findChildByType(R_ANGLE_BRACKET_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getWeakPredKeyword() {
      return findChildByType(WEAK_PRED_KEYWORD);
  }

}
