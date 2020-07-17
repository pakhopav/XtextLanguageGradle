// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.calcDeclaredParameter;
import com.intellij.calcLanguage.calc.psi.calcDefinition;
import com.intellij.calcLanguage.calc.psi.calcExpression;
import com.intellij.calcLanguage.calc.psi.calcVisitor;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiCompositeElementImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.calcLanguage.calc.psi.calcTypes.*;

public class calcDefinitionImpl extends calcPsiCompositeElementImpl implements calcDefinition {

  public calcDefinitionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitDefinition(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<calcDeclaredParameter> getDeclaredParameterList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, calcDeclaredParameter.class);
  }

  @Override
  @NotNull
  public calcExpression getExpression() {
    return findNotNullChildByClass(calcExpression.class);
  }

  @Override
  @NotNull
  public PsiElement getColonKeyword() {
    return findNotNullChildByType(COLON_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getDefKeyword() {
    return findNotNullChildByType(DEF_KEYWORD);
  }

  @Override
  @NotNull
  public PsiElement getId() {
    return findNotNullChildByType(ID);
  }

  @Override
  @Nullable
  public PsiElement getLBracketKeyword() {
    return findChildByType(L_BRACKET_KEYWORD);
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
