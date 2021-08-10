// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtext.language.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.xtext.language.psi.XtextTypes.AS_KEYWORD;
import static com.intellij.xtext.language.psi.XtextTypes.GENERATE_KEYWORD;

public class XtextGeneratedMetamodelImpl extends XtextAbstractMetamodelDeclarationImpl implements XtextGeneratedMetamodel {

    public XtextGeneratedMetamodelImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitGeneratedMetamodel(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor)visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public XtextREFERENCEEPackageSTRING getREFERENCEEPackageSTRING() {
        return findNotNullChildByClass(XtextREFERENCEEPackageSTRING.class);
    }

    @Override
    @NotNull
    public XtextValidID getValidID() {
        return findNotNullChildByClass(XtextValidID.class);
    }

    @Override
    @Nullable
    public XtextValidID1 getValidID1() {
        return findChildByClass(XtextValidID1.class);
    }

    @Override
    @Nullable
    public PsiElement getAsKeyword() {
        return findChildByType(AS_KEYWORD);
    }

    @Override
    @NotNull
  public PsiElement getGenerateKeyword() {
        return findNotNullChildByType(GENERATE_KEYWORD);
  }

}
