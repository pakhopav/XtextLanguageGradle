// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SmalljavaSJAssignment extends PsiElement {

    @Nullable
    SmalljavaSJExpression getSJExpression();

    @NotNull
    SmalljavaSJSelectionExpression getSJSelectionExpression();

    @Nullable
    PsiElement getEqualsKeyword();

}
