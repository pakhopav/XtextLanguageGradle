// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SmalljavaSJIfStatement extends PsiElement {

    @NotNull
    SmalljavaSJExpression getSJExpression();

    @NotNull
    List<SmalljavaSJIfBlock> getSJIfBlockList();

    @Nullable
    PsiElement getElseKeyword();

    @NotNull
    PsiElement getIfKeyword();

    @NotNull
    PsiElement getLBracketKeyword();

    @NotNull
    PsiElement getRBracketKeyword();

}
