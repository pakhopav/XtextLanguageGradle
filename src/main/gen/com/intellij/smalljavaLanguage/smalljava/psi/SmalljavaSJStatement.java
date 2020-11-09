// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.Nullable;

public interface SmalljavaSJStatement extends PsiElement {

    @Nullable
    SmalljavaSJExpression getSJExpression();

    @Nullable
    SmalljavaSJIfStatement getSJIfStatement();

    @Nullable
    SmalljavaSJReturn getSJReturn();

    @Nullable
    SmalljavaSJVariableDeclaration getSJVariableDeclaration();

    @Nullable
    PsiElement getSemicolonKeyword();

}
