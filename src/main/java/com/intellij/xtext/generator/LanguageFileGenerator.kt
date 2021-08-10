package com.intellij.xtext.generator

import java.io.FileOutputStream
import java.io.PrintWriter

class LanguageFileGenerator(extension: String, rootPath: String) :
    com.intellij.xtext.generator.AbstractGenerator(extension, rootPath) {


    fun generateLanguageFile() {
        val file = createOrFindFile(extension.capitalize() + "Language.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir;

            |import com.intellij.lang.Language;

            |public class ${extension.capitalize()}Language extends Language {
            |    public static final ${extension.capitalize()}Language INSTANCE = new ${extension.capitalize()}Language();

            |    private ${extension.capitalize()}Language() {
            |        super("${extension.capitalize()}");

            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }
}