// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJAssignment;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJExpression;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJSelectionExpression;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.EQUALS_KEYWORD;

public class SmalljavaSJAssignmentImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJAssignment {

    public SmalljavaSJAssignmentImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJAssignment(this);
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
    @NotNull
    public SmalljavaSJSelectionExpression getSJSelectionExpression() {
        return findNotNullChildByClass(SmalljavaSJSelectionExpression.class);
    }

    @Override
    @Nullable
    public PsiElement getEqualsKeyword() {
        return findChildByType(EQUALS_KEYWORD);
    }

}
