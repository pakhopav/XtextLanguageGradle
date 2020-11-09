// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJAccessLevel;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.*;

public class SmalljavaSJAccessLevelImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJAccessLevel {

    public SmalljavaSJAccessLevelImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJAccessLevel(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public PsiElement getPrivateKeyword() {
        return findChildByType(PRIVATE_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getProtectedKeyword() {
        return findChildByType(PROTECTED_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getPublicKeyword() {
        return findChildByType(PUBLIC_KEYWORD);
    }

}
