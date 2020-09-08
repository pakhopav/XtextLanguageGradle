package com.intellij.statLanguage.stat;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class StatFileTypeFactory extends FileTypeFactory {
    public StatFileTypeFactory() {
            
    }
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(StatFileType.INSTANCE, "stat");
    }

}