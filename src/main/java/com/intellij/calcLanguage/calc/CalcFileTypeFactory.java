package com.intellij.calcLanguage.calc;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class CalcFileTypeFactory extends FileTypeFactory {
    public CalcFileTypeFactory() {

    }

    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(CalcFileType.INSTANCE, "calc");
    }

}