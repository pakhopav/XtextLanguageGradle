// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJIfBlock1;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import org.jetbrains.annotations.NotNull;

public class SmalljavaSJIfBlock1Impl extends SmalljavaSJIfBlockImpl implements SmalljavaSJIfBlock1 {

    public SmalljavaSJIfBlock1Impl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJIfBlock1(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

}
