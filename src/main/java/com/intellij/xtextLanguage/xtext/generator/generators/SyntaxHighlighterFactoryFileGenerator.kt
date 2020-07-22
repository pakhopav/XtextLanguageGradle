package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class SyntaxHighlighterFactoryFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateSyntaxHighlighterFactoryFile() {
        val file = createFile(extention.capitalize() + "SyntaxHighlighterFactory.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.openapi.fileTypes.*;
            |import com.intellij.openapi.project.Project;
            |import com.intellij.openapi.vfs.VirtualFile;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention.capitalize()}SyntaxHighlighterFactory extends SyntaxHighlighterFactory {
            |    @NotNull
            |    @Override
            |    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
            |        return new ${extention.capitalize()}SyntaxHighlighter();
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}