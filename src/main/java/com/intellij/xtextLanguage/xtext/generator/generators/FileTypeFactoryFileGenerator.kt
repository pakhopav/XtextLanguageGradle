package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class FileTypeFactoryFileGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateFileTypeFactoryFile() {
        val file = createFile(extension.capitalize() + "FileTypeFactory.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir;
            
            |import com.intellij.openapi.fileTypes.*;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extension.capitalize()}FileTypeFactory extends FileTypeFactory {
            |    public ${extension.capitalize()}FileTypeFactory(){
            
            |    }
            |    @Override
            |    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
            |        fileTypeConsumer.consume(${extension.capitalize()}FileType.INSTANCE, "${extension.toLowerCase()}");
            |    }
            
            |}
        """.trimMargin("|"))
        out.close()
    }
}