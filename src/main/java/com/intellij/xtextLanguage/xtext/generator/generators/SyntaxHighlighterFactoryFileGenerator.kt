package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class SyntaxHighlighterFactoryFileGenerator(extension: String, rootPath: String) :
    AbstractGenerator(extension, rootPath) {
    fun generateSyntaxHighlighterFactoryFile() {
        val file = createOrFindFile(extensionCapitalized + "SyntaxHighlighterFactory.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir;
            |import com.intellij.openapi.fileTypes.*;
            |import com.intellij.openapi.project.Project;
            |import com.intellij.openapi.vfs.VirtualFile;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extension.capitalize()}SyntaxHighlighterFactory extends SyntaxHighlighterFactory {
            |    @NotNull
            |    @Override
            |    public SyntaxHighlighter getSyntaxHighlighter(Project project, VirtualFile virtualFile) {
            |        return new ${extension.capitalize()}SyntaxHighlighter();
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}