// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextConditionalBranch extends PsiElement {

    @NotNull
    List<XtextAbstractToken> getAbstractTokenList();

    @Nullable
    XtextDisjunction getDisjunction();

    @Nullable
    XtextUnorderedGroup getUnorderedGroup();

    @Nullable
    PsiElement getLAngleBracketKeyword();

    @Nullable
    PsiElement getRAngleBracketKeyword();

}
