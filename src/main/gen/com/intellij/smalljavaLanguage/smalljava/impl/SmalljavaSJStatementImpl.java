// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.*;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.SEMICOLON_KEYWORD;

public class SmalljavaSJStatementImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJStatement {

    public SmalljavaSJStatementImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJStatement(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public SmalljavaSJExpression getSJExpression() {
        return findChildByClass(SmalljavaSJExpression.class);
    }

    @Override
    @Nullable
    public SmalljavaSJIfStatement getSJIfStatement() {
        return findChildByClass(SmalljavaSJIfStatement.class);
    }

    @Override
    @Nullable
    public SmalljavaSJReturn getSJReturn() {
        return findChildByClass(SmalljavaSJReturn.class);
    }

    @Override
    @Nullable
    public SmalljavaSJVariableDeclaration getSJVariableDeclaration() {
        return findChildByClass(SmalljavaSJVariableDeclaration.class);
    }

    @Override
    @Nullable
    public PsiElement getSemicolonKeyword() {
        return findChildByType(SEMICOLON_KEYWORD);
    }

}
