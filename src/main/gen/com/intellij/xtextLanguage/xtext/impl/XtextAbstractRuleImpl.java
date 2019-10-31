// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.*;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextNamedElementImpl;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextAbstractRuleImpl extends XtextNamedElementImpl implements XtextAbstractRule {

    public XtextAbstractRuleImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitAbstractRule(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public XtextEnumRule getEnumRule() {
        return findChildByClass(XtextEnumRule.class);
    }

    @Override
    @Nullable
    public XtextParserRule getParserRule() {
        return findChildByClass(XtextParserRule.class);
    }

    @Override
    @Nullable
    public XtextTerminalRule getTerminalRule() {
        return findChildByClass(XtextTerminalRule.class);
    }

    @Override
    public String getName() {
        return XtextPsiImplUtil.getName(this);
    }

    @Override
    public PsiElement setName(String newName) {
        return XtextPsiImplUtil.setName(this, newName);
    }

    @Override
    public PsiElement getNameIdentifier() {
        return XtextPsiImplUtil.getNameIdentifier(this);
    }

}
