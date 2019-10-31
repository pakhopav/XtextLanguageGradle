// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractToken;
import com.intellij.xtextLanguage.xtext.psi.XtextDisjunction;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleFromConditionalBranchBranch2;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.L_ANGLE_BRACKET_KEYWORD;
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.R_ANGLE_BRACKET_KEYWORD;

public class XtextRuleFromConditionalBranchBranch2Impl extends XtextPsiCompositeElementImpl implements XtextRuleFromConditionalBranchBranch2 {

    public XtextRuleFromConditionalBranchBranch2Impl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitRuleFromConditionalBranchBranch2(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<XtextAbstractToken> getAbstractTokenList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextAbstractToken.class);
    }

    @Override
    @NotNull
    public XtextDisjunction getDisjunction() {
        return findNotNullChildByClass(XtextDisjunction.class);
    }

    @Override
    @NotNull
    public PsiElement getLAngleBracketKeyword() {
        return findNotNullChildByType(L_ANGLE_BRACKET_KEYWORD);
    }

    @Override
    @NotNull
    public PsiElement getRAngleBracketKeyword() {
        return findNotNullChildByType(R_ANGLE_BRACKET_KEYWORD);
    }

}
