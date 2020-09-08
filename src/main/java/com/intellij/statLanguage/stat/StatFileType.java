package com.intellij.statLanguage.stat;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class StatFileType extends LanguageFileType {
    public static final StatFileType INSTANCE = new StatFileType();

    private StatFileType() {
        super(StatLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Stat file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Stat language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "stat";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return StatIcons.FILE; 
    }
}