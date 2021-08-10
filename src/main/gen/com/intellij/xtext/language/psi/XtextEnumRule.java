// This is a generated file. Not intended for manual editing.
package com.intellij.xtext.language.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextEnumRule extends XtextAbstractRule {

    @NotNull
    List<XtextAnnotation> getAnnotationList();

    @NotNull
    XtextEnumLiterals getEnumLiterals();

    @Nullable
    XtextTypeRef getTypeRef();

    @NotNull
    XtextValidID getValidID();

    @NotNull
    PsiElement getColonKeyword();

    @NotNull
    PsiElement getEnumKeyword();

    @Nullable
    PsiElement getReturnsKeyword();

    @NotNull
    PsiElement getSemicolonKeyword();

}
