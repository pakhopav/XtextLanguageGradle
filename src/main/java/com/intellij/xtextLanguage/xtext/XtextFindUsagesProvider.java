package com.intellij.xtextLanguage.xtext;

import com.intellij.lang.HelpID;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractMetamodelDeclaration;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractRule;
import com.intellij.xtextLanguage.xtext.psi.XtextGrammar;
import com.intellij.xtextLanguage.xtext.psi.XtextParameter;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

public class XtextFindUsagesProvider implements FindUsagesProvider {

    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new XtextWordScanner();
    }

    @Nullable
    @Override
    public String getHelpId(@NotNull PsiElement psiElement) {
        return HelpID.FIND_OTHER_USAGES;
    }

    @NotNull
    @Override
    public String getDescriptiveName(@NotNull PsiElement element) {
        if (element instanceof PsiNamedElement) {
            return ((PsiNamedElement) element).getName();
        } else {
            return "";
        }
    }

    @NotNull
    @Override
    public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
        if (element instanceof PsiNamedElement) {
            return element.getText();
        } else {
            return "";
        }
    }


    @Override
    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
        return psiElement instanceof XtextGrammar ||
                psiElement instanceof XtextAbstractRule ||
                psiElement instanceof XtextAbstractMetamodelDeclaration ||
                psiElement instanceof XtextParameter;
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement element) {
        if (element instanceof XtextGrammar) {
            return "XtextGrammar";
        } else if (element instanceof XtextAbstractRule) {
            return "XtextAbstractRule";
        } else if (element instanceof XtextAbstractMetamodelDeclaration) {
            return "XtextAbstractMetamodelDeclaration";
        } else if (element instanceof XtextParameter) {
            return "XtextParameter";
        } else {
            return "";
        }
    }
}
