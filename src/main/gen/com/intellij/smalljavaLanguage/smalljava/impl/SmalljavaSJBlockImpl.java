// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJBlock;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJIfBlock;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJMethodBody;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmalljavaSJBlockImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJBlock {

    public SmalljavaSJBlockImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJBlock(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public SmalljavaSJIfBlock getSJIfBlock() {
        return findChildByClass(SmalljavaSJIfBlock.class);
    }

    @Override
    @Nullable
    public SmalljavaSJMethodBody getSJMethodBody() {
        return findChildByClass(SmalljavaSJMethodBody.class);
    }

}
