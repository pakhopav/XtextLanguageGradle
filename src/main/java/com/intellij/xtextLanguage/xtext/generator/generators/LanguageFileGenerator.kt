package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class LanguageFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {


    fun generateLanguageFile() {
        val file = createFile(extention + "Language.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;

            |import com.intellij.lang.Language;

            |public class ${extention}Language extends Language {
            |    public static final ${extention}Language INSTANCE = new ${extention}Language();

            |    private ${extention}Language() {
            |        super("${extention}");

            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }
}