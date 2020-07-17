// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.calcAbstractDefinition;
import com.intellij.calcLanguage.calc.psi.calcDeclaredParameter;
import com.intellij.calcLanguage.calc.psi.calcDefinition;
import com.intellij.calcLanguage.calc.psi.calcVisitor;
import com.intellij.calcLanguage.calc.psi.impl.calcNamedElementImpl;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiImplUtil;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class calcAbstractDefinitionImpl extends calcNamedElementImpl implements calcAbstractDefinition {

  public calcAbstractDefinitionImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitAbstractDefinition(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @Nullable
  public calcDeclaredParameter getDeclaredParameter() {
    return findChildByClass(calcDeclaredParameter.class);
  }

  @Override
  @Nullable
  public calcDefinition getDefinition() {
    return findChildByClass(calcDefinition.class);
  }

  @Override
  public String getName() {
    return calcPsiImplUtil.getName(this);
  }

  @Override
  public PsiElement setName(String newName) {
    return calcPsiImplUtil.setName(this, newName);
  }

  @Override
  public PsiElement getNameIdentifier() {
    return calcPsiImplUtil.getNameIdentifier(this);
  }

}
