// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextGroup;
import com.intellij.xtextLanguage.xtext.psi.XtextUnorderedGroup;
import com.intellij.xtextLanguage.xtext.psi.XtextUnorderedGroupSuffix1;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextUnorderedGroupImpl extends XtextPsiCompositeElementImpl implements XtextUnorderedGroup {

  public XtextUnorderedGroupImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitUnorderedGroup(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public XtextGroup getGroup() {
        return findNotNullChildByClass(XtextGroup.class);
    }

    @Override
    @Nullable
    public XtextUnorderedGroupSuffix1 getUnorderedGroupSuffix1() {
        return findChildByClass(XtextUnorderedGroupSuffix1.class);
    }

}
