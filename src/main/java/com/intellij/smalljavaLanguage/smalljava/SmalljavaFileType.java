package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class SmalljavaFileType extends LanguageFileType {
    public static final SmalljavaFileType INSTANCE = new SmalljavaFileType();

    private SmalljavaFileType() {
        super(SmalljavaLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Smalljava file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Smalljava language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "smalljava";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return SmalljavaIcons.FILE;
    }
}