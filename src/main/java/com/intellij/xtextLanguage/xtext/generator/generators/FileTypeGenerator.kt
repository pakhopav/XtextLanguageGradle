package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class FileTypeGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateFileTypeFile() {
        val file = createOrFindFile(extension.capitalize() + "FileType.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir;

            |import com.intellij.openapi.fileTypes.LanguageFileType;
            |import org.jetbrains.annotations.*;
            |import javax.swing.*;

            |public class ${extension.capitalize()}FileType extends LanguageFileType {
            |    public static final ${extension.capitalize()}FileType INSTANCE = new ${extension.capitalize()}FileType();

            |    private ${extension.capitalize()}FileType() {
            |        super(${extension.capitalize()}Language.INSTANCE);
            |    }

            |    @NotNull
            |    @Override
            |    public String getName() {
            |        return "${extension.capitalize()} file";
            |    }
            
            |    @NotNull
            |    @Override
            |    public String getDescription() {
            |        return "${extension.capitalize()} language file";
            |    }
            
            |    @NotNull
            |    @Override
            |    public String getDefaultExtension() {
            |        return "${extension.toLowerCase()}";
            |    }
            
            |    @Nullable
            |    @Override
            |    public Icon getIcon() {
            |        return ${extension.capitalize()}Icons.FILE; 
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }
}