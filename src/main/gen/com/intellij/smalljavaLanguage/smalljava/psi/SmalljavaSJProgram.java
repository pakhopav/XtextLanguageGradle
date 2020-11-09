// This is a generated file. Not intended for manual editing.
package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SmalljavaSJProgram extends PsiElement {

    @Nullable
    SmalljavaQualifiedName getQualifiedName();

    @NotNull
    List<SmalljavaSJClass> getSJClassList();

    @NotNull
    List<SmalljavaSJImport> getSJImportList();

    @Nullable
    PsiElement getPackageKeyword();

    @Nullable
    PsiElement getSemicolonKeyword();

}
