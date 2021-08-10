package com.intellij.xtext.language.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.xtext.language.XtextFileType;
import com.intellij.xtext.language.XtextLanguage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class XtextFile extends PsiFileBase {
    public XtextFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, XtextLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return XtextFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Xtext File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}
