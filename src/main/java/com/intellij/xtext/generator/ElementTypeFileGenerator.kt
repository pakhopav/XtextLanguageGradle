package com.intellij.xtext.generator

import java.io.FileOutputStream
import java.io.PrintWriter

class ElementTypeFileGenerator(extension: String, rootPath: String) :
    com.intellij.xtext.generator.AbstractGenerator(extension, rootPath) {
    fun generateElementTypeFile() {
        val file = createOrFindFile(extension.capitalize() + "ElementType.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
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