package com.intellij.smalljavaLanguage.smalljava.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;

public abstract class SmalljavaNamedElementImpl extends SmalljavaPsiCompositeElementImpl implements PsiNameIdentifierOwner {
    public SmalljavaNamedElementImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public int getTextOffset() {
        if (this.getNameIdentifier() != null) {
            return this.getNameIdentifier().getNode().getStartOffset();
        }
        return super.getTextOffset();
    }
}