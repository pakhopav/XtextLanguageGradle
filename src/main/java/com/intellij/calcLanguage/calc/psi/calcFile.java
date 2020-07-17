package com.intellij.calcLanguage.calc.psi;

import com.intellij.calcLanguage.calc.calcFileType;
import com.intellij.calcLanguage.calc.calcLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class calcFile extends PsiFileBase {
    public calcFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, calcLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return calcFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "calc File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
