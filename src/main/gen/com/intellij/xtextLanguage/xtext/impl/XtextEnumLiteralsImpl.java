// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextEnumLiteralDeclaration;
import com.intellij.xtextLanguage.xtext.psi.XtextEnumLiterals;
import com.intellij.xtextLanguage.xtext.psi.XtextEnumLiteralsSuffix1;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextEnumLiteralsImpl extends XtextPsiCompositeElementImpl implements XtextEnumLiterals {

  public XtextEnumLiteralsImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitEnumLiterals(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public XtextEnumLiteralDeclaration getEnumLiteralDeclaration() {
        return findNotNullChildByClass(XtextEnumLiteralDeclaration.class);
    }

    @Override
    @Nullable
    public XtextEnumLiteralsSuffix1 getEnumLiteralsSuffix1() {
        return findChildByClass(XtextEnumLiteralsSuffix1.class);
    }

}
