// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.xtextLanguage.xtext.psi.XtextParameter;
import com.intellij.xtextLanguage.xtext.psi.XtextVisitor;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextNamedElementImpl;
import com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiImplUtil;
import org.jetbrains.annotations.NotNull;

import static com.intellij.xtextLanguage.xtext.psi.XtextTypes.ID;

public class XtextParameterImpl extends XtextNamedElementImpl implements XtextParameter {

  public XtextParameterImpl(@NotNull ASTNode node) {
    super(node);
  }

  public void accept(@NotNull XtextVisitor visitor) {
    visitor.visitParameter(this);
  }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof XtextVisitor) accept((XtextVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public PsiElement getId() {
        return findNotNullChildByType(ID);
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
