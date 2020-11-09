// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaREFERENCESJClassQualifiedName;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJParameter;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import org.jetbrains.annotations.NotNull;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.ID;

public class SmalljavaSJParameterImpl extends SmalljavaSJSymbolImpl implements SmalljavaSJParameter {

    public SmalljavaSJParameterImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJParameter(this);
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
    public PsiElement getId() {
        return findNotNullChildByType(ID);
    }

}
