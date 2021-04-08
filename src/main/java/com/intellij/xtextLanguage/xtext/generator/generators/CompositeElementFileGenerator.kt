package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class CompositeElementFileGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateCompositeElementFile() {
        val file = createFile(extension.capitalize() + "PsiCompositeElementImpl.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir.psi.impl;
            |import com.intellij.extapi.psi.ASTWrapperPsiElement;
            |import com.intellij.lang.ASTNode;
            |import com.intellij.psi.PsiReference;
            |import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extension.capitalize()}PsiCompositeElementImpl extends ASTWrapperPsiElement {
            |    public ${extension.capitalize()}PsiCompositeElementImpl(@NotNull ASTNode node) {
            |        super(node);
            |    }
            
            |    @NotNull
            |    @Override
            |    public PsiReference[] getReferences() {
            |        return ReferenceProvidersRegistry.getReferencesFromProviders(this);
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}