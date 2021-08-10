// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextEnumLiteralDeclaration;
import com.intellij.xtext.language.psi.XtextKeyword;
import com.intellij.xtext.language.psi.XtextREFERENCEEEnumLiteralID;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtext.language.psi.XtextTypes.EQUALS_KEYWORD;

public class XtextEnumLiteralDeclarationImpl extends XtextPsiCompositeElementImpl implements XtextEnumLiteralDeclaration {

  public XtextEnumLiteralDeclarationImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitEnumLiteralDeclaration(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @Nullable
  public XtextKeyword getKeyword() {
    return findChildByClass(XtextKeyword.class);
  }

    @Override
    @NotNull
    public XtextREFERENCEEEnumLiteralID getREFERENCEEEnumLiteralID() {
        return findNotNullChildByClass(XtextREFERENCEEEnumLiteralID.class);
    }

  @Override
  @Nullable
  public PsiElement getEqualsKeyword() {
      return findChildByType(EQUALS_KEYWORD);
  }

}
