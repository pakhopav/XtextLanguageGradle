package com.intellij.xtext.language;

import com.intellij.openapi.fileTypes.FileTypeConsumer;
import com.intellij.openapi.fileTypes.FileTypeFactory;
import org.jetbrains.annotations.NotNull;

public class XtextFileTypeFactory extends FileTypeFactory {
    public XtextFileTypeFactory(){

    }
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(XtextFileType.INSTANCE, "xtext");
    }

}