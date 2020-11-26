package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.lang.HelpID;
import com.intellij.lang.cacheBuilder.WordsScanner;
import com.intellij.lang.findUsages.FindUsagesProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiNamedElement;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJClass;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJMember;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJSymbol;
import com.sun.istack.NotNull;
import com.sun.istack.Nullable;

public class SmalljavaFindUsagesProvider implements FindUsagesProvider {

    @Nullable
    @Override
    public WordsScanner getWordsScanner() {
        return new SmalljavaWordScanner();
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
        return psiElement instanceof SmalljavaSJClass ||
                psiElement instanceof SmalljavaSJMember ||
                psiElement instanceof SmalljavaSJSymbol;
    }

    @NotNull
    @Override
    public String getType(@NotNull PsiElement element) {
        if (element instanceof SmalljavaSJClass) {
            return "SmalljavaSJClass";
        } else if (element instanceof SmalljavaSJMember) {
            return "SmalljavaSJMember";
        } else if (element instanceof SmalljavaSJSymbol) {
            return "SmalljavaSJSymbol";
        } else {
            return "";
        }
    }
}
