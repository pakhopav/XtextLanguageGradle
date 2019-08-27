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

public class XtextRuleNameAndParamsImpl extends XtextPsiCompositeElementImpl implements XtextRuleNameAndParams {

  public XtextRuleNameAndParamsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitRuleNameAndParams(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<XtextParameter> getParameterList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextParameter.class);
  }

  @Override
  @NotNull
  public XtextValidID getValidID() {
    return findNotNullChildByClass(XtextValidID.class);
  }

  @Override
  @Nullable
  public PsiElement getLAngleBracket() {
    return findChildByType(L_ANGLE_BRACKET);
  }

  @Override
  @Nullable
  public PsiElement getRAngleBracket() {
    return findChildByType(R_ANGLE_BRACKET);
  }

}
