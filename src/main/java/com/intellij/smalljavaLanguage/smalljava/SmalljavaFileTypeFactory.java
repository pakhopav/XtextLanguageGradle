package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class SmalljavaFileTypeFactory extends FileTypeFactory {
    public SmalljavaFileTypeFactory() {

    }

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(SmalljavaFileType.INSTANCE, "smalljava");
    }

}