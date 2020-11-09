// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.smalljavaLanguage.smalljava.psi.*;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SmalljavaSJSelectionExpressionImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJSelectionExpression {

    public SmalljavaSJSelectionExpressionImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJSelectionExpression(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public List<SmalljavaREFERENCESJMemberID> getREFERENCESJMemberIDList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, SmalljavaREFERENCESJMemberID.class);
    }

    @Override
    @NotNull
    public List<SmalljavaSJExpression> getSJExpressionList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, SmalljavaSJExpression.class);
    }

    @Override
    @NotNull
    public SmalljavaSJTerminalExpression getSJTerminalExpression() {
        return findNotNullChildByClass(SmalljavaSJTerminalExpression.class);
    }

}
