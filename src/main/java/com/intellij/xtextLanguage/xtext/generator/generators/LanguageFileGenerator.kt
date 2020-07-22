package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class LanguageFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {


    fun generateLanguageFile() {
        val file = createFile(extention.capitalize() + "Language.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;

            |import com.intellij.lang.Language;

            |public class ${extention.capitalize()}Language extends Language {
            |    public static final ${extention.capitalize()}Language INSTANCE = new ${extention.capitalize()}Language();

            |    private ${extention.capitalize()}Language() {
            |        super("${extention.capitalize()}");

            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }
}