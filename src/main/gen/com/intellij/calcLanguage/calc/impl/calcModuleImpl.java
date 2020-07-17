// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.impl;

import com.intellij.calcLanguage.calc.psi.calcImport;
import com.intellij.calcLanguage.calc.psi.calcModule;
import com.intellij.calcLanguage.calc.psi.calcStatement;
import com.intellij.calcLanguage.calc.psi.calcVisitor;
import com.intellij.calcLanguage.calc.psi.impl.calcNamedElementImpl;
import com.intellij.calcLanguage.calc.psi.impl.calcPsiImplUtil;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.intellij.calcLanguage.calc.psi.calcTypes.ID;
import static com.intellij.calcLanguage.calc.psi.calcTypes.MODULE_KEYWORD;

public class calcModuleImpl extends calcNamedElementImpl implements calcModule {

  public calcModuleImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull calcVisitor visitor) {
    visitor.visitModule(this);
  }

  public void accept(@NotNull PsiElementVisitor visitor) {
    if (visitor instanceof calcVisitor) accept((calcVisitor) visitor);
    else super.accept(visitor);
  }

  @Override
  @NotNull
  public List<calcImport> getImportList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, calcImport.class);
  }

  @Override
  @NotNull
  public List<calcStatement> getStatementList() {
    return PsiTreeUtil.getChildrenOfTypeAsList(this, calcStatement.class);
  }

  @Override
  @NotNull
  public PsiElement getId() {
    return findNotNullChildByType(ID);
  }

  @Override
  @NotNull
  public PsiElement getModuleKeyword() {
    return findNotNullChildByType(MODULE_KEYWORD);
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
