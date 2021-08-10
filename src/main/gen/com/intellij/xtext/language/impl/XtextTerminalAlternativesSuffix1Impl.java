// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtext.language.psi.XtextTerminalAlternativesSuffix1;
import com.intellij.xtext.language.psi.XtextTerminalGroup;
import com.intellij.xtext.language.psi.XtextVisitor;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class XtextTerminalAlternativesSuffix1Impl extends XtextPsiCompositeElementImpl implements XtextTerminalAlternativesSuffix1 {

    public XtextTerminalAlternativesSuffix1Impl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitTerminalAlternativesSuffix1(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<XtextTerminalGroup> getTerminalGroupList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextTerminalGroup.class);
    }

}
