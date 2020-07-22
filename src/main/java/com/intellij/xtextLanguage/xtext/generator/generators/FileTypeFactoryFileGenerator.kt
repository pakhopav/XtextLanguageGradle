package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class FileTypeFactoryFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateFileTypeFactoryFile() {
        val file = createFile(extention.capitalize() + "FileTypeFactory.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.openapi.fileTypes.*;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention.capitalize()}FileTypeFactory extends FileTypeFactory {
            |    public ${extention.capitalize()}FileTypeFactory(){
            
            |    }
            |    @Override
            |    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
            |        fileTypeConsumer.consume(${extention.capitalize()}FileType.INSTANCE, "${extention.toLowerCase()}");
            |    }
            
            |}
        """.trimMargin("|"))
        out.close()
    }
}