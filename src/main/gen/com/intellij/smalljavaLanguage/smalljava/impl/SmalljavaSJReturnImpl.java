// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJExpression;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJReturn;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.RETURN_KEYWORD;
import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.SEMICOLON_KEYWORD;

public class SmalljavaSJReturnImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJReturn {

    public SmalljavaSJReturnImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJReturn(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public SmalljavaSJExpression getSJExpression() {
        return findNotNullChildByClass(SmalljavaSJExpression.class);
    }

    @Override
    @NotNull
    public PsiElement getReturnKeyword() {
        return findNotNullChildByType(RETURN_KEYWORD);
    }

    @Override
    @NotNull
    public PsiElement getSemicolonKeyword() {
        return findNotNullChildByType(SEMICOLON_KEYWORD);
    }

}
