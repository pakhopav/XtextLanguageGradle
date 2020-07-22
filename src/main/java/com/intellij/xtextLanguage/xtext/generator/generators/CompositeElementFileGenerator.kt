package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class CompositeElementFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateCompositeElementFile() {
        val file = createFile(extention.capitalize() + "PsiCompositeElementImpl.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi.impl;
            |import com.intellij.extapi.psi.ASTWrapperPsiElement;
            |import com.intellij.lang.ASTNode;
            |import com.intellij.psi.PsiReference;
            |import com.intellij.psi.impl.source.resolve.reference.ReferenceProvidersRegistry;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention.capitalize()}PsiCompositeElementImpl extends ASTWrapperPsiElement {
            |    public ${extention.capitalize()}PsiCompositeElementImpl(@NotNull ASTNode node) {
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