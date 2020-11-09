// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJMember;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaNamedElementImpl;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiImplUtil;
import org.jetbrains.annotations.NotNull;

public abstract class SmalljavaSJMemberImpl extends SmalljavaNamedElementImpl implements SmalljavaSJMember {

    public SmalljavaSJMemberImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJMember(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    public String getName() {
        return SmalljavaPsiImplUtil.getName(this);
    }

    @Override
    public PsiElement setName(String newName) {
        return SmalljavaPsiImplUtil.setName(this, newName);
    }

    @Override
    public PsiElement getNameIdentifier() {
        return SmalljavaPsiImplUtil.getNameIdentifier(this);
    }

}
