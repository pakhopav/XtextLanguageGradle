package com.intellij.xtextLanguage.xtext.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import com.intellij.xtextLanguage.xtext.XtextFileType;
import com.intellij.xtextLanguage.xtext.XtextLanguage;
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
