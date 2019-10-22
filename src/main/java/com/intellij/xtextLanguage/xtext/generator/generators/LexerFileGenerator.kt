package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class LexerFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateLexerFile() {
        val file = createFile(extention + "Lexer.java", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.grammar;
            
            |import com.intellij.lexer.FlexAdapter;
            |import $packageDir._${extention}Lexer;
            |
            |public class ${extention}Lexer extends FlexAdapter {

            |    public ${extention}Lexer() {
            |        super(new _${extention}Lexer());
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }
}