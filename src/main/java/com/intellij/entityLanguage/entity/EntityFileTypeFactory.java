package com.intellij.entityLanguage.entity;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class EntityFileTypeFactory extends FileTypeFactory {
    public EntityFileTypeFactory() {

    }

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(EntityFileType.INSTANCE, "entity");
    }

}