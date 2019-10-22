package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class NamedElementFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun genenerateNamedElementFile() {
        val file = createFile(extention + "NamedElementImpl.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi.impl;
            
            |import com.intellij.lang.ASTNode;
            |import org.jetbrains.annotations.NotNull;
            |import com.intellij.psi.PsiNameIdentifierOwner;
            
            |public abstract class ${extention}NamedElementImpl extends ${extention}PsiCompositeElementImpl implements PsiNameIdentifierOwner {
            |    public ${extention}NamedElementImpl(@NotNull ASTNode node) {
            |        super(node);
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }
}