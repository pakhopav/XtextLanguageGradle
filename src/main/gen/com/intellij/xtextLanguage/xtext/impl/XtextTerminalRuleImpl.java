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

public class XtextTerminalRuleImpl extends XtextAbstractRuleImpl implements XtextTerminalRule {

  public XtextTerminalRuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitTerminalRule(this);
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
  @Nullable
  public XtextTerminalAlternatives getTerminalAlternatives() {
    return findChildByClass(XtextTerminalAlternatives.class);
  }

  @Override
  @Nullable
  public XtextTypeRef getTypeRef() {
    return findChildByClass(XtextTypeRef.class);
  }

  @Override
  @Nullable
  public XtextValidID getValidID() {
    return findChildByClass(XtextValidID.class);
  }

  @Override
  @Nullable
  public PsiElement getColon() {
    return findChildByType(COLON);
  }

  @Override
  @Nullable
  public PsiElement getFragment() {
    return findChildByType(FRAGMENT);
  }

  @Override
  @Nullable
  public PsiElement getReturns() {
    return findChildByType(RETURNS);
  }

  @Override
  @Nullable
  public PsiElement getSemicolon() {
    return findChildByType(SEMICOLON);
  }

  @Override
  @NotNull
  public PsiElement getTerminal() {
    return findNotNullChildByType(TERMINAL);
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
