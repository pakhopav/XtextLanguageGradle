// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SmalljavaSJClass extends PsiNameIdentifierOwner {

    @Nullable
    SmalljavaREFERENCESJClassQualifiedName getREFERENCESJClassQualifiedName();

    @NotNull
    List<SmalljavaSJMember> getSJMemberList();

    @NotNull
    PsiElement getClassKeyword();

    @Nullable
    PsiElement getExtendsKeyword();

    @NotNull
    PsiElement getId();

    @NotNull
    PsiElement getLBraceKeyword();

    @NotNull
    PsiElement getRBraceKeyword();

    String getName();

    PsiElement setName(String newName);

    PsiElement getNameIdentifier();

}
