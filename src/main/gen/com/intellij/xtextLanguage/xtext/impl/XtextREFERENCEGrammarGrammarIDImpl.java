// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextGrammarID;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEGrammarGrammarID;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

public class XtextREFERENCEGrammarGrammarIDImpl extends XtextPsiCompositeElementImpl implements XtextREFERENCEGrammarGrammarID {

  public XtextREFERENCEGrammarGrammarIDImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitREFERENCEGrammarGrammarID(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

  @Override
  @NotNull
  public XtextGrammarID getGrammarID() {
    return findNotNullChildByClass(XtextGrammarID.class);
  }

}
