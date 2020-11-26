package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.lang.refactoring.RefactoringSupportProvider;
import com.intellij.psi.PsiElement;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJClass;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJMember;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaSJSymbol;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SmalljavaRefactoringSupportProvider extends RefactoringSupportProvider {

    @Override
    public boolean isMemberInplaceRenameAvailable(@NotNull PsiElement elementToRename, @Nullable PsiElement context) {
        return elementToRename instanceof SmalljavaSJClass ||
                elementToRename instanceof SmalljavaSJMember ||
                elementToRename instanceof SmalljavaSJSymbol;
    }

}