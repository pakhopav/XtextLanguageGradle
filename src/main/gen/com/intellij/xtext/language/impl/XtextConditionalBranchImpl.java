// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtext.language.psi.*;
import com.intellij.xtext.language.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.xtext.language.psi.XtextTypes.L_ANGLE_BRACKET_KEYWORD;
import static com.intellij.xtext.language.psi.XtextTypes.R_ANGLE_BRACKET_KEYWORD;

public class XtextConditionalBranchImpl extends XtextPsiCompositeElementImpl implements XtextConditionalBranch {

    public XtextConditionalBranchImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitConditionalBranch(this);
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

    @Override
    @Nullable
    public XtextDisjunction getDisjunction() {
        return findChildByClass(XtextDisjunction.class);
    }

    @Override
    @Nullable
    public XtextUnorderedGroup getUnorderedGroup() {
        return findChildByClass(XtextUnorderedGroup.class);
    }

    @Override
    @Nullable
    public PsiElement getLAngleBracketKeyword() {
        return findChildByType(L_ANGLE_BRACKET_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getRAngleBracketKeyword() {
        return findChildByType(R_ANGLE_BRACKET_KEYWORD);
    }

}
