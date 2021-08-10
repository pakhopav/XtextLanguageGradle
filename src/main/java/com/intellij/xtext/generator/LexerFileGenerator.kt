package com.intellij.xtext.generator

import java.io.FileOutputStream
import java.io.PrintWriter

class LexerFileGenerator(extension: String, rootPath: String) :
    com.intellij.xtext.generator.AbstractGenerator(extension, rootPath) {
    fun generateLexerFile() {
        val file = createOrFindFile(extension.capitalize() + "Lexer.java", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir.grammar;
            
            |import com.intellij.lexer.FlexAdapter;
            |import $packageDir._${extension.capitalize()}Lexer;
            |
            |public class ${extension.capitalize()}Lexer extends FlexAdapter {

            |    public ${extension.capitalize()}Lexer() {
            |        super(new _${extension.capitalize()}Lexer());
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }
}