// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.XtextTerminalAlternatives;
import com.intellij.xtext.language.psi.XtextTerminalAlternativesSuffix1;
import com.intellij.xtext.language.psi.XtextTerminalGroup;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextTerminalAlternativesImpl extends XtextPsiCompositeElementImpl implements XtextTerminalAlternatives {

  public XtextTerminalAlternativesImpl(@NotNull ASTNode node) {
    super(node);
  }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitTerminalAlternatives(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public XtextTerminalAlternativesSuffix1 getTerminalAlternativesSuffix1() {
        return findChildByClass(XtextTerminalAlternativesSuffix1.class);
    }

    @Override
    @NotNull
    public XtextTerminalGroup getTerminalGroup() {
        return findNotNullChildByClass(XtextTerminalGroup.class);
    }

}
