// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaQualifiedNameWithWildcard;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJImport;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.IMPORT_KEYWORD;
import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.SEMICOLON_KEYWORD;

public class SmalljavaSJImportImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJImport {

    public SmalljavaSJImportImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJImport(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @NotNull
    public SmalljavaQualifiedNameWithWildcard getQualifiedNameWithWildcard() {
        return findNotNullChildByClass(SmalljavaQualifiedNameWithWildcard.class);
    }

    @Override
    @NotNull
    public PsiElement getImportKeyword() {
        return findNotNullChildByType(IMPORT_KEYWORD);
    }

    @Override
    @NotNull
    public PsiElement getSemicolonKeyword() {
        return findNotNullChildByType(SEMICOLON_KEYWORD);
    }

}
