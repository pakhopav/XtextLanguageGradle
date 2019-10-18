// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface XtextRuleFromConditionalBranchGroup extends PsiElement {

    @NotNull
    List<XtextAbstractToken> getAbstractTokenList();

    @NotNull
    XtextDisjunction getDisjunction();

    @NotNull
    PsiElement getLAngleBracket();

    @NotNull
    PsiElement getRAngleBracket();

}
