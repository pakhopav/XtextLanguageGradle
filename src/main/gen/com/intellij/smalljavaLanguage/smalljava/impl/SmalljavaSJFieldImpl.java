// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaREFERENCESJClassQualifiedName;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJAccessLevel;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJField;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.ID;
import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.SEMICOLON_KEYWORD;

public class SmalljavaSJFieldImpl extends SmalljavaSJMemberImpl implements SmalljavaSJField {

    public SmalljavaSJFieldImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJField(this);
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
    public PsiElement getId() {
        return findNotNullChildByType(ID);
    }

    @Override
    @NotNull
    public PsiElement getSemicolonKeyword() {
        return findNotNullChildByType(SEMICOLON_KEYWORD);
    }

}
