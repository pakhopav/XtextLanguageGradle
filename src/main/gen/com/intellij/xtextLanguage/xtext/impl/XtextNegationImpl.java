// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAtom;
import com.intellij.xtextLanguage.xtext.psi.XtextNegation;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.ACX_MARK_KEYWORD;

public class XtextNegationImpl extends XtextPsiCompositeElementImpl implements XtextNegation {

    public XtextNegationImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitNegation(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public XtextAtom getAtom() {
        return findChildByClass(XtextAtom.class);
    }

    @Override
    @Nullable
    public XtextNegation getNegation() {
        return findChildByClass(XtextNegation.class);
    }

    @Override
    @Nullable
    public PsiElement getAcxMarkKeyword() {
        return findChildByType(ACX_MARK_KEYWORD);
    }

}
