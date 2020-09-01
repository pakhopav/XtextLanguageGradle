package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class ElementTypeFileGenerator(extension: String) : AbstractGenerator(extension) {
    fun generateElementTypeFile() {
        val file = createFile(extension.capitalize() + "ElementType.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            
            |import $packageDir.${extension.capitalize()}Language;
            |import com.intellij.psi.tree.IElementType;
            |import org.jetbrains.annotations.*;
            
            |public class ${extension.capitalize()}ElementType extends IElementType {
            |    private String debugName;
            |    public ${extension.capitalize()}ElementType(@NotNull @NonNls String debugName) {
            |        super(debugName, ${extension.capitalize()}Language.INSTANCE);
            |        this.debugName = debugName;
            |    }
            |    public String getDebugName(){
            |        return debugName;
            |    }
            |}
        """.trimMargin("|"))
        out.close()
    }

}