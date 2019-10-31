// This is a generated file. Not intended for manual editing.
package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNameIdentifierOwner;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface XtextGrammar extends PsiNameIdentifierOwner {

    @NotNull
    List<XtextAbstractMetamodelDeclaration> getAbstractMetamodelDeclarationList();

    @NotNull
    List<XtextAbstractRule> getAbstractRuleList();

    @NotNull
    XtextGrammarID getGrammarID();

    @NotNull
    List<XtextREFERENCEAbstractRuleRuleID> getREFERENCEAbstractRuleRuleIDList();

    @NotNull
    List<XtextREFERENCEGrammarGrammarID> getREFERENCEGrammarGrammarIDList();

    @NotNull
    PsiElement getGrammarKeyword();

    @Nullable
    PsiElement getHiddenKeyword();

    @Nullable
    PsiElement getLBracketKeyword();

    @Nullable
    PsiElement getRBracketKeyword();

    @Nullable
    PsiElement getWithKeyword();

    String getName();

    PsiElement setName(String newName);

    PsiElement getNameIdentifier();

}
