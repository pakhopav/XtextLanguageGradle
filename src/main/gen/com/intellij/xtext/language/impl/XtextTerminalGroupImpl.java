// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextTerminalGroup;
import com.intellij.xtext.language.psi.XtextTerminalGroupSuffix1;
import com.intellij.xtext.language.psi.XtextTerminalToken;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextTerminalGroupImpl extends XtextPsiCompositeElementImpl implements XtextTerminalGroup {

  public XtextTerminalGroupImpl(@NotNull ASTNode node) {
    super(node);
  }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitTerminalGroup(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public XtextTerminalGroupSuffix1 getTerminalGroupSuffix1() {
        return findChildByClass(XtextTerminalGroupSuffix1.class);
    }

    @Override
    @NotNull
    public XtextTerminalToken getTerminalToken() {
        return findNotNullChildByClass(XtextTerminalToken.class);
    }

}
