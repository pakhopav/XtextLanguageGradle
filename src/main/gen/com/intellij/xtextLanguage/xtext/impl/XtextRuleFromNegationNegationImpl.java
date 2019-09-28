// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextNegation;
import com.intellij.xtextLanguage.xtext.psi.XtextRuleFromNegationNegation;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.ACX_MARK;

public class XtextRuleFromNegationNegationImpl extends XtextPsiCompositeElementImpl implements XtextRuleFromNegationNegation {

    public XtextRuleFromNegationNegationImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitRuleFromNegationNegation(this);
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
    public PsiElement getAcxMark() {
        return findNotNullChildByType(ACX_MARK);
    }

}
