package com.intellij.xtextLanguage.xtext;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.*;

import javax.swing.*;

public class XtextFileType extends LanguageFileType {
    public static final XtextFileType INSTANCE = new XtextFileType();

    private XtextFileType() {
        super(XtextLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Xtext file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Xtext language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "xtext";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return XtextIcons.FILE;
    }
}