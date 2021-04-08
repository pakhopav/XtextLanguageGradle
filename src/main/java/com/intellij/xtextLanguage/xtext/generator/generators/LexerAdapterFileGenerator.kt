package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class LexerAdapterFileGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateLexerAdapterFile() {
        val file = createFile(extension.capitalize() + "LexerAdapter.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir;
            |import com.intellij.lexer.FlexAdapter;
            |import java.io.Reader;
            
            |public class ${extension.capitalize()}LexerAdapter extends FlexAdapter {
            |    public ${extension.capitalize()}LexerAdapter() {
            |        super(new _${extension.capitalize()}Lexer((Reader) null));
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}