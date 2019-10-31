// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextNegation;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleFromNegationBranch2;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.ACX_MARK_KEYWORD;

public class XtextRuleFromNegationBranch2Impl extends XtextPsiCompositeElementImpl implements XtextRuleFromNegationBranch2 {

    public XtextRuleFromNegationBranch2Impl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitRuleFromNegationBranch2(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public XtextNegation getNegation() {
        return findNotNullChildByClass(XtextNegation.class);
    }

    @Override
    @NotNull
    public PsiElement getAcxMarkKeyword() {
        return findNotNullChildByType(ACX_MARK_KEYWORD);
    }

}
