// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJExpression;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJIfBlock;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJIfStatement;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.*;

public class SmalljavaSJIfStatementImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJIfStatement {

    public SmalljavaSJIfStatementImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJIfStatement(this);
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
    public List<SmalljavaSJIfBlock> getSJIfBlockList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, SmalljavaSJIfBlock.class);
    }

    @Override
    @Nullable
    public PsiElement getElseKeyword() {
        return findChildByType(ELSE_KEYWORD);
    }

    @Override
    @NotNull
    public PsiElement getIfKeyword() {
        return findNotNullChildByType(IF_KEYWORD);
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
