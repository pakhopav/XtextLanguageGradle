// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SmalljavaSJMethod extends SmalljavaSJMember {

    @NotNull
    SmalljavaREFERENCESJClassQualifiedName getREFERENCESJClassQualifiedName();

    @Nullable
    SmalljavaSJAccessLevel getSJAccessLevel();

    @NotNull
    SmalljavaSJMethodBody getSJMethodBody();

    @NotNull
    List<SmalljavaSJParameter> getSJParameterList();

    @NotNull
    PsiElement getId();

    @NotNull
    PsiElement getLBracketKeyword();

    @NotNull
    PsiElement getRBracketKeyword();

}
