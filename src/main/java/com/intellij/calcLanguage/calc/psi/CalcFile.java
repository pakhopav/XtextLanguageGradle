package com.intellij.calcLanguage.calc.psi;

import com.intellij.calcLanguage.calc.CalcFileType;
import com.intellij.calcLanguage.calc.CalcLanguage;
import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class CalcFile extends PsiFileBase {
    public CalcFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, CalcLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return CalcFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Calc File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
