// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJIfBlock;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJStatement;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.L_BRACE_KEYWORD;
import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.R_BRACE_KEYWORD;

public class SmalljavaSJIfBlockImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJIfBlock {

    public SmalljavaSJIfBlockImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJIfBlock(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<SmalljavaSJStatement> getSJStatementList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, SmalljavaSJStatement.class);
    }

    @Override
    @Nullable
    public PsiElement getLBraceKeyword() {
        return findChildByType(L_BRACE_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getRBraceKeyword() {
        return findChildByType(R_BRACE_KEYWORD);
    }

}
