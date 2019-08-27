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

public class XtextEnumRuleImpl extends XtextAbstractRuleImpl implements XtextEnumRule {

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
  public PsiElement getColon() {
    return findNotNullChildByType(COLON);
  }

  @Override
  @NotNull
  public PsiElement getEnum() {
    return findNotNullChildByType(ENUM);
  }

  @Override
  @Nullable
  public PsiElement getReturns() {
    return findChildByType(RETURNS);
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
