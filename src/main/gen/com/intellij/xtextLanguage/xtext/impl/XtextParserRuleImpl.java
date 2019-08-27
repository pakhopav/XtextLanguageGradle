// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import java.util.List;
import org.jetbrains.annotations.*;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextAbstractRuleImpl;
import com.intellij.xtextLanguage.xtext.psi.*;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiImplUtil;

public class XtextParserRuleImpl extends XtextAbstractRuleImpl implements XtextParserRule {

  public XtextParserRuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitParserRule(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public XtextAlternatives getAlternatives() {
    return findNotNullChildByClass(XtextAlternatives.class);
  }

  @Override
  @NotNull
  public List<XtextAnnotation> getAnnotationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextAnnotation.class);
  }

  @Override
  @NotNull
  public List<XtextREFERENCEAbstractRuleRuleID> getREFERENCEAbstractRuleRuleIDList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextREFERENCEAbstractRuleRuleID.class);
  }

  @Override
  @NotNull
  public XtextRuleNameAndParams getRuleNameAndParams() {
    return findNotNullChildByClass(XtextRuleNameAndParams.class);
  }

  @Override
  @Nullable
  public XtextTypeRef getTypeRef() {
    return findChildByClass(XtextTypeRef.class);
  }

  @Override
  @Nullable
  public PsiElement getAsterisk() {
    return findChildByType(ASTERISK);
  }

  @Override
  @NotNull
  public PsiElement getColon() {
    return findNotNullChildByType(COLON);
  }

  @Override
  @Nullable
  public PsiElement getFragment() {
    return findChildByType(FRAGMENT);
  }

  @Override
  @Nullable
  public PsiElement getHidden() {
    return findChildByType(HIDDEN);
  }

  @Override
  @Nullable
  public PsiElement getLBracket() {
    return findChildByType(L_BRACKET);
  }

  @Override
  @Nullable
  public PsiElement getReturns() {
    return findChildByType(RETURNS);
  }

  @Override
  @Nullable
  public PsiElement getRBracket() {
    return findChildByType(R_BRACKET);
  }

  @Override
  @NotNull
  public PsiElement getSemicolon() {
    return findNotNullChildByType(SEMICOLON);
  }

  @Override
  public String getName() {
    return XtextPsiImplUtil.getName(this);
  }

  @Override
  public PsiElement setName(String newName) {
    return XtextPsiImplUtil.setName(this, newName);
  }

  @Override
  public PsiElement getNameIdentifier() {
    return XtextPsiImplUtil.getNameIdentifier(this);
  }

}
