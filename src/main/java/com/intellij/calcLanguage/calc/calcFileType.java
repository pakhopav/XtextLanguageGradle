package com.intellij.calcLanguage.calc;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class calcFileType extends LanguageFileType {
    public static final calcFileType INSTANCE = new calcFileType();

    private calcFileType() {
        super(calcLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "calc file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "calc language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "calc";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return calcIcons.FILE;
    }
}