package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class LexerAdapterFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateLexerAdapterFile() {
        val file = createFile(extention.capitalize() + "LexerAdapter.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.lexer.FlexAdapter;
            |import java.io.Reader;
            
            |public class ${extention.capitalize()}LexerAdapter extends FlexAdapter {
            |    public ${extention.capitalize()}LexerAdapter() {
            |        super(new _${extention.capitalize()}Lexer((Reader) null));
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}