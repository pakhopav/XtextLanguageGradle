package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class NamedElementFileGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun genenerateNamedElementFile() {
        val file = createOrFindFile(extension.capitalize() + "NamedElementImpl.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir.psi.impl;
            
            |import com.intellij.lang.ASTNode;
            |import org.jetbrains.annotations.NotNull;
            |import com.intellij.psi.PsiNameIdentifierOwner;
            
            |public abstract class ${extension.capitalize()}NamedElementImpl extends ${extension.capitalize()}PsiCompositeElementImpl implements PsiNameIdentifierOwner {
            |    public ${extension.capitalize()}NamedElementImpl(@NotNull ASTNode node) {
            |        super(node);
            |    }
            |    
            |    @Override
            |    public int getTextOffset() {
            |        if(this.getNameIdentifier() != null){
            |            return this.getNameIdentifier().getNode().getStartOffset();
            |        }
            |        return super.getTextOffset();
            |    }
            |}
        """.trimMargin("|"))
        out.close()

    }
}