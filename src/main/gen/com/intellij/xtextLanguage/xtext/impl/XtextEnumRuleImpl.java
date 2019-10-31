// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.*;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*;

public class XtextEnumRuleImpl extends XtextPsiCompositeElementImpl implements XtextEnumRule {

  public XtextEnumRuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitEnumRule(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<XtextAnnotation> getAnnotationList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextAnnotation.class);
  }

  @Override
  @NotNull
  public XtextEnumLiterals getEnumLiterals() {
      return findNotNullChildByClass(XtextEnumLiterals.class);
  }

  @Override
  @Nullable
  public XtextTypeRef getTypeRef() {
    return findChildByClass(XtextTypeRef.class);
  }

  @Override
  @NotNull
  public XtextValidID getValidID() {
      return findNotNullChildByClass(XtextValidID.class);
  }

  @Override
  @NotNull
  public PsiElement getColonKeyword() {
      return findNotNullChildByType(COLON_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getEnumKeyword() {
      return findNotNullChildByType(ENUM_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getReturnsKeyword() {
      return findChildByType(RETURNS_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getSemicolonKeyword() {
      return findNotNullChildByType(SEMICOLON_KEYWORD);
  }

}
