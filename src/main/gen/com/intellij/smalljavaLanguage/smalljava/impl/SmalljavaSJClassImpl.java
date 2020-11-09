// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaREFERENCESJClassQualifiedName;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJClass;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJMember;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaVisitor;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaNamedElementImpl;
import com.intellij.smalljavaLanguage.smalljava.psi.impl.SmalljavaPsiImplUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaTypes.*;

public class SmalljavaSJClassImpl extends SmalljavaNamedElementImpl implements SmalljavaSJClass {

    public SmalljavaSJClassImpl(@NotNull ASTNode node) {
        super(node);
    }

    public void accept(@NotNull SmalljavaVisitor visitor) {
        visitor.visitSJClass(this);
    }

    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof SmalljavaVisitor) accept((SmalljavaVisitor) visitor);
        else super.accept(visitor);
    }

    @Override
    @Nullable
    public SmalljavaREFERENCESJClassQualifiedName getREFERENCESJClassQualifiedName() {
        return findChildByClass(SmalljavaREFERENCESJClassQualifiedName.class);
    }

    @Override
    @NotNull
    public List<SmalljavaSJMember> getSJMemberList() {
        return PsiTreeUtil.getChildrenOfTypeAsList(this, SmalljavaSJMember.class);
    }

    @Override
    @NotNull
    public PsiElement getClassKeyword() {
        return findNotNullChildByType(CLASS_KEYWORD);
    }

    @Override
    @Nullable
    public PsiElement getExtendsKeyword() {
        return findChildByType(EXTENDS_KEYWORD);
    }

    @Override
    @NotNull
    public PsiElement getId() {
        return findNotNullChildByType(ID);
    }

    @Override
    @NotNull
    public PsiElement getLBraceKeyword() {
        return findNotNullChildByType(L_BRACE_KEYWORD);
    }

    @Override
    @NotNull
    public PsiElement getRBraceKeyword() {
        return findNotNullChildByType(R_BRACE_KEYWORD);
    }

    @Override
    public String getName() {
        return SmalljavaPsiImplUtil.getName(this);
    }

    @Override
    public PsiElement setName(String newName) {
        return SmalljavaPsiImplUtil.setName(this, newName);
    }

    @Override
    public PsiElement getNameIdentifier() {
        return SmalljavaPsiImplUtil.getNameIdentifier(this);
    }

}
