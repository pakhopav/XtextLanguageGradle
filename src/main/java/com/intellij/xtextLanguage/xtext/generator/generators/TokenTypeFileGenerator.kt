package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class TokenTypeFileGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateTokenTypeFile() {
        val file = createFile(extension.capitalize() + "TokenType.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir.psi;
            
            |import com.intellij.psi.tree.IElementType;
            |import $packageDir.${extension.capitalize()}Language;
            |import org.jetbrains.annotations.NonNls;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extension.capitalize()}TokenType extends IElementType {
            |    @NotNull
            |    private final String myDebugName;
            |    public ${extension.capitalize()}TokenType(@NotNull @NonNls String debugName) {
            |        super(debugName, ${extension.capitalize()}Language.INSTANCE);
            |        myDebugName = debugName;
            |    }
            |    public String getDebugName(){
            |        return myDebugName;
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }
}