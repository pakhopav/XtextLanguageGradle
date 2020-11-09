// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaREFERENCESJClassQualifiedName;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJExpression;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJVariableDeclaration;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import org.jetbrains.annotations.NotNull;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.*;

public class SmalljavaSJVariableDeclarationImpl extends SmalljavaSJSymbolImpl implements SmalljavaSJVariableDeclaration {

    public SmalljavaSJVariableDeclarationImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJVariableDeclaration(this);
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
    @NotNull
    public SmalljavaSJExpression getSJExpression() {
        return findNotNullChildByClass(SmalljavaSJExpression.class);
    }

    @Override
    @NotNull
    public PsiElement getEqualsKeyword() {
        return findNotNullChildByType(EQUALS_KEYWORD);
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
