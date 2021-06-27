// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractToken;
import com.intellij.xtextLanguage.xtext.psi.XtextGroupSuffix1;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class XtextGroupSuffix1Impl extends XtextPsiCompositeElementImpl implements XtextGroupSuffix1 {

    public XtextGroupSuffix1Impl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitGroupSuffix1(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<XtextAbstractToken> getAbstractTokenList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextAbstractToken.class);
    }

}
