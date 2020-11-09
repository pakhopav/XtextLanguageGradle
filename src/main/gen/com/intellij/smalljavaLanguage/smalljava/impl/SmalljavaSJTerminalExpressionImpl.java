// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.*;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.*;

public class SmalljavaSJTerminalExpressionImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJTerminalExpression {

    public SmalljavaSJTerminalExpressionImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJTerminalExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public SmalljavaREFERENCESJClassQualifiedName getREFERENCESJClassQualifiedName() {
        return findChildByClass(SmalljavaREFERENCESJClassQualifiedName.class);
    }

    @Override
    @Nullable
    public SmalljavaREFERENCESJSymbolID getREFERENCESJSymbolID() {
        return findChildByClass(SmalljavaREFERENCESJSymbolID.class);
    }

    @Override
    @Nullable
    public SmalljavaSJExpression getSJExpression() {
        return findChildByClass(SmalljavaSJExpression.class);
    }

    @Override
    @Nullable
    public PsiElement getFalseKeyword() {
        return findChildByType(FALSE_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getInt() {
        return findChildByType(INT);
    }

    @Override
    @Nullable
    public PsiElement getLBracketKeyword() {
        return findChildByType(L_BRACKET_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getNewKeyword() {
        return findChildByType(NEW_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getNullKeyword() {
        return findChildByType(NULL_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getRBracketKeyword() {
        return findChildByType(R_BRACKET_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getString() {
        return findChildByType(STRING);
    }

    @Override
    @Nullable
    public PsiElement getSuperKeyword() {
        return findChildByType(SUPER_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getThisKeyword() {
        return findChildByType(THIS_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getTrueKeyword() {
        return findChildByType(TRUE_KEYWORD);
    }

}
