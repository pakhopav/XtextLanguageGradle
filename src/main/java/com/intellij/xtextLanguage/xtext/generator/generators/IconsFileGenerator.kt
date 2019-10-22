package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class IconsFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateIconsFile() {
        val file = createFile(extention + "Icons.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.openapi.util.IconLoader;
            
            |import javax.swing.*;
            
            |public class ${extention}Icons {
            |    public static final Icon FILE = IconLoader.getIcon("/icons/simpleIcon.png");
            |}
        """.trimMargin("|"))
        out.close()
    }
}