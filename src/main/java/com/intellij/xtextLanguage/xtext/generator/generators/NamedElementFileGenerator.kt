package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class NamedElementFileGenerator(extension: String) : AbstractGenerator(extension) {
    fun genenerateNamedElementFile() {
        val file = createFile(extension.capitalize() + "NamedElementImpl.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi.impl;
            
            |import com.intellij.lang.ASTNode;
            |import org.jetbrains.annotations.NotNull;
            |import com.intellij.psi.PsiNameIdentifierOwner;
            
            |public abstract class ${extension.capitalize()}NamedElementImpl extends ${extension.capitalize()}PsiCompositeElementImpl implements PsiNameIdentifierOwner {
            |    public ${extension.capitalize()}NamedElementImpl(@NotNull ASTNode node) {
            |        super(node);
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }
}