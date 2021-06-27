// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.*;

public class XtextParserRuleImpl extends XtextAbstractRuleImpl implements XtextParserRule {

    public XtextParserRuleImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitParserRule(this);
    }

    @Override
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
    public List<XtextParameter> getParameterList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextParameter.class);
    }

    @Override
    @NotNull
    public List<XtextREFERENCEAbstractRuleRuleID> getREFERENCEAbstractRuleRuleIDList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextREFERENCEAbstractRuleRuleID.class);
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
    @Nullable
    public PsiElement getAsteriskKeyword() {
        return findChildByType(ASTERISK_KEYWORD);
    }

    @Override
    @NotNull
    public PsiElement getColonKeyword() {
        return findNotNullChildByType(COLON_KEYWORD);
  }

  @Override
  @Nullable
  public PsiElement getFragmentKeyword() {
      return findChildByType(FRAGMENT_KEYWORD);
  }

    @Override
    @Nullable
    public PsiElement getHiddenKeyword() {
        return findChildByType(HIDDEN_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getLAngleBracketKeyword() {
        return findChildByType(L_ANGLE_BRACKET_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getLBracketKeyword() {
        return findChildByType(L_BRACKET_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getReturnsKeyword() {
        return findChildByType(RETURNS_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getRAngleBracketKeyword() {
        return findChildByType(R_ANGLE_BRACKET_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getRBracketKeyword() {
        return findChildByType(R_BRACKET_KEYWORD);
    }

    @Override
    @NotNull
    public PsiElement getSemicolonKeyword() {
        return findNotNullChildByType(SEMICOLON_KEYWORD);
  }

}
