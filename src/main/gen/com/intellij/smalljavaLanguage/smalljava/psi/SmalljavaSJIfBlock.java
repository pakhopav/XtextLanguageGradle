// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SmalljavaSJIfBlock extends PsiElement {

    @NotNull
    List<SmalljavaSJStatement> getSJStatementList();

    @Nullable
    PsiElement getLBraceKeyword();

    @Nullable
    PsiElement getRBraceKeyword();

}
