// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJAssignment;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJExpression;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

public class SmalljavaSJExpressionImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJExpression {

    public SmalljavaSJExpressionImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public SmalljavaSJAssignment getSJAssignment() {
        return findNotNullChildByClass(SmalljavaSJAssignment.class);
    }

}
