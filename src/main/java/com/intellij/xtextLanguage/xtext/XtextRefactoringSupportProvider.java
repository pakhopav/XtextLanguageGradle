package com.intellij.xtextLanguage.xtext;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractMetamodelDeclaration;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractRule;
import com.intellij.xtextLanguage.xtext.psi.XtextGrammar;
import com.intellij.xtextLanguage.xtext.psi.XtextParameter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class XtextRefactoringSupportProvider extends RefactoringSupportProvider {

    @Override
    public boolean isMemberInplaceRenameAvailable(@NotNull PsiElement elementToRename, @Nullable PsiElement context) {
        return elementToRename instanceof XtextGrammar ||
                elementToRename instanceof XtextAbstractRule ||
                elementToRename instanceof XtextAbstractMetamodelDeclaration ||
                elementToRename instanceof XtextParameter;
    }

}