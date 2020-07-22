package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class LexerFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateLexerFile() {
        val file = createFile(extention.capitalize() + "Lexer.java", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.grammar;
            
            |import com.intellij.lexer.FlexAdapter;
            |import $packageDir._${extention.capitalize()}Lexer;
            |
            |public class ${extention.capitalize()}Lexer extends FlexAdapter {

            |    public ${extention.capitalize()}Lexer() {
            |        super(new _${extention.capitalize()}Lexer());
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }
}