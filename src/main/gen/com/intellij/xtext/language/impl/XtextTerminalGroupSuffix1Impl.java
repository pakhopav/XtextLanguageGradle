// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtext.language.psi.XtextTerminalGroupSuffix1;
import com.intellij.xtext.language.psi.XtextTerminalToken;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class XtextTerminalGroupSuffix1Impl extends XtextPsiCompositeElementImpl implements XtextTerminalGroupSuffix1 {

    public XtextTerminalGroupSuffix1Impl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitTerminalGroupSuffix1(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<XtextTerminalToken> getTerminalTokenList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextTerminalToken.class);
    }

}
