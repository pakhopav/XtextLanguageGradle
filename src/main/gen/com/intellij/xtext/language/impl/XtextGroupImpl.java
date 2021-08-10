// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextAbstractToken;
import com.intellij.xtext.language.psi.XtextGroup;
import com.intellij.xtext.language.psi.XtextGroupSuffix1;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextGroupImpl extends XtextPsiCompositeElementImpl implements XtextGroup {

  public XtextGroupImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitGroup(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public XtextAbstractToken getAbstractToken() {
        return findNotNullChildByClass(XtextAbstractToken.class);
    }

    @Override
    @Nullable
    public XtextGroupSuffix1 getGroupSuffix1() {
        return findChildByClass(XtextGroupSuffix1.class);
    }

}
