package com.intellij.entityLanguage.entity;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class EntityFileType extends LanguageFileType {
    public static final EntityFileType INSTANCE = new EntityFileType();

    private EntityFileType() {
        super(EntityLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "Entity file";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "Entity language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "entity";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return EntityIcons.FILE;
    }
}