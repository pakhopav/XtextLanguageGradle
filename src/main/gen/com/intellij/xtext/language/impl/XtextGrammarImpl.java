// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtext.language.psi.*;
import com.intellij.xtext.language.psi.impl.XtextNamedElementImpl;
import com.intellij.xtext.language.psi.impl.XtextPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.xtext.language.psi.XtextTypes.*;

public class XtextGrammarImpl extends XtextNamedElementImpl implements XtextGrammar {

    public XtextGrammarImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitGrammar(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<XtextAbstractMetamodelDeclaration> getAbstractMetamodelDeclarationList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextAbstractMetamodelDeclaration.class);
    }

    @Override
    @NotNull
    public List<XtextAbstractRule> getAbstractRuleList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextAbstractRule.class);
    }

    @Override
    @NotNull
    public XtextGrammarID getGrammarID() {
        return findNotNullChildByClass(XtextGrammarID.class);
    }

    @Override
    @NotNull
    public List<XtextREFERENCEAbstractRuleRuleID> getREFERENCEAbstractRuleRuleIDList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextREFERENCEAbstractRuleRuleID.class);
    }

    @Override
    @NotNull
    public List<XtextREFERENCEGrammarGrammarID> getREFERENCEGrammarGrammarIDList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, XtextREFERENCEGrammarGrammarID.class);
    }

    @Override
    @NotNull
    public PsiElement getGrammarKeyword() {
        return findNotNullChildByType(GRAMMAR_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getHiddenKeyword() {
        return findChildByType(HIDDEN_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getLBracketKeyword() {
        return findChildByType(L_BRACKET_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getRBracketKeyword() {
        return findChildByType(R_BRACKET_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getWithKeyword() {
        return findChildByType(WITH_KEYWORD);
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
