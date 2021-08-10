package com.intellij.xtext.generator

import java.io.FileOutputStream
import java.io.PrintWriter

class RootFileGenerator(extension: String, rootPath: String) :
    com.intellij.xtext.generator.AbstractGenerator(extension, rootPath) {
    fun generateRootFileFile() {
        val file = createOrFindFile(extension.capitalize() + "File.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir.psi;
            |import com.intellij.extapi.psi.PsiFileBase;
            |import com.intellij.openapi.fileTypes.FileType;
            |import com.intellij.psi.FileViewProvider;
            |import $packageDir.*;
            |import org.jetbrains.annotations.NotNull;
            |import javax.swing.*;
            
            |public class ${extension.capitalize()}File extends PsiFileBase {
            |    public ${extension.capitalize()}File(@NotNull FileViewProvider viewProvider) {
            |        super(viewProvider, ${extension.capitalize()}Language.INSTANCE);
            |    }
                
            |    @NotNull
            |    @Override
            |    public FileType getFileType() {
            |        return ${extension.capitalize()}FileType.INSTANCE;
            |    }
            
            |    @Override
            |    public String toString() {
            |        return "${extension.capitalize()} File";
            |    }
            
            |    @Override
            |    public Icon getIcon(int flags) {
            |        return super.getIcon(flags);
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}