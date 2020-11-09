// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.smalljavaLanguage.smalljava.psi.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.*;

public class SmalljavaSJMethodImpl extends SmalljavaSJMemberImpl implements SmalljavaSJMethod {

    public SmalljavaSJMethodImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJMethod(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public SmalljavaREFERENCESJClassQualifiedName getREFERENCESJClassQualifiedName() {
        return findNotNullChildByClass(SmalljavaREFERENCESJClassQualifiedName.class);
    }

    @Override
    @Nullable
    public SmalljavaSJAccessLevel getSJAccessLevel() {
        return findChildByClass(SmalljavaSJAccessLevel.class);
    }

    @Override
    @NotNull
    public SmalljavaSJMethodBody getSJMethodBody() {
        return findNotNullChildByClass(SmalljavaSJMethodBody.class);
    }

    @Override
    @NotNull
    public List<SmalljavaSJParameter> getSJParameterList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, SmalljavaSJParameter.class);
    }

    @Override
    @NotNull
    public PsiElement getId() {
        return findNotNullChildByType(ID);
    }

    @Override
    @NotNull
    public PsiElement getLBracketKeyword() {
        return findNotNullChildByType(L_BRACKET_KEYWORD);
    }

    @Override
    @NotNull
    public PsiElement getRBracketKeyword() {
        return findNotNullChildByType(R_BRACKET_KEYWORD);
    }

}
