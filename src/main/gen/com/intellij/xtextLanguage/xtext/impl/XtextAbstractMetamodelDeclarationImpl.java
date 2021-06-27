// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractMetamodelDeclaration;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextNamedElementImpl;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiImplUtil;
import org.jetbrains.annotations.NotNull;

public abstract class XtextAbstractMetamodelDeclarationImpl extends XtextNamedElementImpl implements XtextAbstractMetamodelDeclaration {

    public XtextAbstractMetamodelDeclarationImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull XtextVisitor visitor) {
        visitor.visitAbstractMetamodelDeclaration(this);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
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
