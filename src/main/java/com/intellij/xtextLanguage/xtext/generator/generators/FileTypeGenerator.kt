package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class FileTypeGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateFileTypeFile() {
        val file = createFile(extention.capitalize() + "FileType.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;

            |import com.intellij.openapi.fileTypes.LanguageFileType;
            |import org.jetbrains.annotations.*;
            |import javax.swing.*;

            |public class ${extention.capitalize()}FileType extends LanguageFileType {
            |    public static final ${extention.capitalize()}FileType INSTANCE = new ${extention.capitalize()}FileType();

            |    private ${extention.capitalize()}FileType() {
            |        super(${extention.capitalize()}Language.INSTANCE);
            |    }

            |    @NotNull
            |    @Override
            |    public String getName() {
            |        return "${extention.capitalize()} file";
            |    }
            
            |    @NotNull
            |    @Override
            |    public String getDescription() {
            |        return "${extention.capitalize()} language file";
            |    }
            
            |    @NotNull
            |    @Override
            |    public String getDefaultExtension() {
            |        return "${extention.toLowerCase()}";
            |    }
            
            |    @Nullable
            |    @Override
            |    public Icon getIcon() {
            |        return ${extention.capitalize()}Icons.FILE; 
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }
}