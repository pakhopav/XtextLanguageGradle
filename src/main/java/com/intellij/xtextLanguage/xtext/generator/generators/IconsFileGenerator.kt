package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class IconsFileGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateIconsFile() {
        val file = createFile(extension.capitalize() + "Icons.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir;
            
            |import com.intellij.openapi.util.IconLoader;
            
            |import javax.swing.*;
            
            |public class ${extension.capitalize()}Icons {
            |    public static final Icon FILE = IconLoader.getIcon("/icons/${extension}Icon.png");
            |}
        """.trimMargin("|"))
        out.close()
    }
}