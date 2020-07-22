package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class TokenTupeFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateTokenTypeFile() {
        val file = createFile(extention.capitalize() + "TokenType.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            
            |import com.intellij.psi.tree.IElementType;
            |import $packageDir.${extention.capitalize()}Language;
            |import org.jetbrains.annotations.NonNls;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention.capitalize()}TokenType extends IElementType {
            |    @NotNull
            |    private final String myDebugName;
            |    public ${extention.capitalize()}TokenType(@NotNull @NonNls String debugName) {
            |        super(debugName, ${extention.capitalize()}Language.INSTANCE);
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