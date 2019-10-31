// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleFromLiteralConditionBranch1;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.FALSE_KEYWORD;
import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.TRUE_KEYWORD;

public class XtextRuleFromLiteralConditionBranch1Impl extends XtextPsiCompositeElementImpl implements XtextRuleFromLiteralConditionBranch1 {

    public XtextRuleFromLiteralConditionBranch1Impl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitRuleFromLiteralConditionBranch1(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public PsiElement getFalseKeyword() {
        return findChildByType(FALSE_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getTrueKeyword() {
        return findChildByType(TRUE_KEYWORD);
    }

}
