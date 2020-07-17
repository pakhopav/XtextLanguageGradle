// This is a generated file. Not intended for manual editing.
package com.intellij.calcLanguage.calc.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

public class calcVisitor extends PsiElementVisitor {

  public void visitAbstractDefinition(@NotNull calcAbstractDefinition o) {
    visitPsiNameIdentifierOwner(o);
  }

  public void visitAddition(@NotNull calcAddition o) {
    visitPsiElement(o);
  }

  public void visitDeclaredParameter(@NotNull calcDeclaredParameter o) {
    visitPsiElement(o);
  }

  public void visitDefinition(@NotNull calcDefinition o) {
    visitPsiElement(o);
  }

  public void visitEvaluation(@NotNull calcEvaluation o) {
    visitPsiElement(o);
  }

  public void visitExpression(@NotNull calcExpression o) {
    visitPsiElement(o);
  }

  public void visitImport(@NotNull calcImport o) {
    visitPsiElement(o);
  }

  public void visitModule(@NotNull calcModule o) {
    visitPsiNameIdentifierOwner(o);
  }

  public void visitMultiplication(@NotNull calcMultiplication o) {
    visitPsiElement(o);
  }

  public void visitPrimaryExpression(@NotNull calcPrimaryExpression o) {
    visitPsiElement(o);
  }

  public void visitPrimaryExpression1(@NotNull calcPrimaryExpression1 o) {
    visitPsiElement(o);
  }

  public void visitPrimaryExpression2(@NotNull calcPrimaryExpression2 o) {
    visitPsiElement(o);
  }

  public void visitPrimaryExpression3(@NotNull calcPrimaryExpression3 o) {
    visitPsiElement(o);
  }

  public void visitREFERENCEAbstractDefinitionID(@NotNull calcREFERENCEAbstractDefinitionID o) {
    visitPsiElement(o);
  }

  public void visitREFERENCEModuleID(@NotNull calcREFERENCEModuleID o) {
    visitPsiElement(o);
  }

  public void visitStatement(@NotNull calcStatement o) {
    visitPsiElement(o);
  }

  public void visitPsiNameIdentifierOwner(@NotNull PsiNameIdentifierOwner o) {
    visitElement(o);
  }

  public void visitPsiElement(@NotNull PsiElement o) {
    visitElement(o);
  }

}
