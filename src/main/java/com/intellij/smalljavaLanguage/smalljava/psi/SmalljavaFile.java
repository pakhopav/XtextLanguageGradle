package com.intellij.smalljavaLanguage.smalljava.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.smalljavaLanguage.smalljava.SmalljavaFileType;
import com.intellij.smalljavaLanguage.smalljava.SmalljavaLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class SmalljavaFile extends PsiFileBase {
    public SmalljavaFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, SmalljavaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return SmalljavaFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Smalljava File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
