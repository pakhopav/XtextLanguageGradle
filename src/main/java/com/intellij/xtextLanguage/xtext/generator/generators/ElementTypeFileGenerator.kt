package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class ElementTypeFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateElementTypeFile() {
        val file = createFile(extention.capitalize() + "ElementType.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            
            |import $packageDir.${extention.capitalize()}Language;
            |import com.intellij.psi.tree.IElementType;
            |import org.jetbrains.annotations.*;
            
            |public class ${extention.capitalize()}ElementType extends IElementType {
            |    private String debugName;
            |    public ${extention.capitalize()}ElementType(@NotNull @NonNls String debugName) {
            |        super(debugName, ${extention.capitalize()}Language.INSTANCE);
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