package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class RootFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateRootFileFile() {
        val file = createFile(extention.capitalize() + "File.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            |import com.intellij.extapi.psi.PsiFileBase;
            |import com.intellij.openapi.fileTypes.FileType;
            |import com.intellij.psi.FileViewProvider;
            |import $packageDir.*;
            |import org.jetbrains.annotations.NotNull;
            |import javax.swing.*;
            
            |public class ${extention.capitalize()}File extends PsiFileBase {
            |    public ${extention.capitalize()}File(@NotNull FileViewProvider viewProvider) {
            |        super(viewProvider, ${extention.capitalize()}Language.INSTANCE);
            |    }
                
            |    @NotNull
            |    @Override
            |    public FileType getFileType() {
            |        return ${extention.capitalize()}FileType.INSTANCE;
            |    }
            
            |    @Override
            |    public String toString() {
            |        return "${extention.capitalize()} File";
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