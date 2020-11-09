// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.smalljavaLanguage.smalljava.psi.*;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiCompositeElementImpl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.PACKAGE_KEYWORD;
import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.SEMICOLON_KEYWORD;

public class SmalljavaSJProgramImpl extends SmalljavaPsiCompositeElementImpl implements SmalljavaSJProgram {

    public SmalljavaSJProgramImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJProgram(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public SmalljavaQualifiedName getQualifiedName() {
        return findChildByClass(SmalljavaQualifiedName.class);
    }

    @Override
    @NotNull
    public List<SmalljavaSJClass> getSJClassList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, SmalljavaSJClass.class);
    }

    @Override
    @NotNull
    public List<SmalljavaSJImport> getSJImportList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, SmalljavaSJImport.class);
    }

    @Override
    @Nullable
    public PsiElement getPackageKeyword() {
        return findChildByType(PACKAGE_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getSemicolonKeyword() {
        return findChildByType(SEMICOLON_KEYWORD);
    }

}
