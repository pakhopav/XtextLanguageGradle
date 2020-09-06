package com.intellij.statLanguage.stat.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.statLanguage.stat.StatFileType;
import com.intellij.statLanguage.stat.StatLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class StatFile extends PsiFileBase {
    public StatFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, StatLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return StatFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Stat File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
