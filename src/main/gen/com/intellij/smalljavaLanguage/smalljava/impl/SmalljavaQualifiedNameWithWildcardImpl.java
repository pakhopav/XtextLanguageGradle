// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaQualifiedName;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaQualifiedNameWithWildcard;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.KEYWORD_1;

public class SmalljavaQualifiedNameWithWildcardImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaQualifiedNameWithWildcard {

    public SmalljavaQualifiedNameWithWildcardImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitQualifiedNameWithWildcard(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public SmalljavaQualifiedName getQualifiedName() {
        return findNotNullChildByClass(SmalljavaQualifiedName.class);
    }

    @Override
    @Nullable
    public PsiElement getKeyword1() {
        return findChildByType(KEYWORD_1);
    }

}
