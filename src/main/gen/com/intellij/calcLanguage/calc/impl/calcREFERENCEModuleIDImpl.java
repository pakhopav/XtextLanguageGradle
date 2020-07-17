// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.calcREFERENCEModuleID;
import com.intellij.calcLanguage.calc.psi.calcVisitor;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiCompositeElementImpl;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;

import static com.intellij.calcLanguage.calc.psi.calcTypes.ID;

public class calcREFERENCEModuleIDImpl extends calcPsiCompositeElementImpl implements calcREFERENCEModuleID {

  public calcREFERENCEModuleIDImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitREFERENCEModuleID(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public PsiElement getId() {
    return findNotNullChildByType(ID);
  }

}
