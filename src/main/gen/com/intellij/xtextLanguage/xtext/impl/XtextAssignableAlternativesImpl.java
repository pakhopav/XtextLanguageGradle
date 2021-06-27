// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAssignableAlternatives;
import com.intellij.xtextLanguage.xtext.psi.XtextAssignableAlternativesSuffix1;
import com.intellij.xtextLanguage.xtext.psi.XtextAssignableTerminal;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextAssignableAlternativesImpl extends XtextPsiCompositeElementImpl implements XtextAssignableAlternatives {

  public XtextAssignableAlternativesImpl(@NotNull ASTNode node) {
    super(node);
  }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitAssignableAlternatives(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public XtextAssignableAlternativesSuffix1 getAssignableAlternativesSuffix1() {
        return findChildByClass(XtextAssignableAlternativesSuffix1.class);
    }

    @Override
    @NotNull
    public XtextAssignableTerminal getAssignableTerminal() {
        return findNotNullChildByClass(XtextAssignableTerminal.class);
    }

}
