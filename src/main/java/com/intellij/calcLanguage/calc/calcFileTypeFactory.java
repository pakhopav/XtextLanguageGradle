package com.intellij.calcLanguage.calc;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class calcFileTypeFactory extends FileTypeFactory {
    public calcFileTypeFactory() {

    }

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(calcFileType.INSTANCE, "calc");
    }

}