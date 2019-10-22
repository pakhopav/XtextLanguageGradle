package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class ReferenceContributorFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateReferenceContributorFile() {
        val file = createFile(extention + "ReferenceContributor.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.openapi.util.TextRange;
            |import com.intellij.patterns.PlatformPatterns;
            |import com.intellij.psi.*;
            |import com.intellij.util.ProcessingContext;
            |import $packageDir.psi.*;
            |import org.jetbrains.annotations.NotNull;
            
            |import java.util.ArrayList;
            |import java.util.Arrays;
            |import java.util.Collection;
            
            |public class ${extention}ReferenceContributor extends PsiReferenceContributor {
            |@Override
            |public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
            
        """.trimMargin("|"))
        fileModel.referencesModel.references.forEach {
            if (it.targets.size == 0) return@forEach
            val referenceName = it.name.replace("_", "")
            out.print("""
            |registrar.registerReferenceProvider(PlatformPatterns.psiElement(${extention}${referenceName}.class).withLanguage(${extention}Language.INSTANCE),
            |    new PsiReferenceProvider() {
            |        @NotNull
            |        @Override
            |        public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
            |                                                     @NotNull ProcessingContext context){
            |            ${extention}${referenceName} reference = (${extention}${referenceName}) element;
            |            String value = reference.getText();
            |            ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>)Arrays.asList(
            """.trimMargin("|"))
            val targets = it.targets
            targets.forEach {
                out.print("${extention}${it.name}.class")
                if (it !== targets.last()) out.print(", ")
            }
            out.print("));\n")
            out.print("""
            |            return new PsiReference[]{
            |                new ${extention}Reference(element, new TextRange(0, value.length()), list)};
            |            }
            |        });
            """.trimMargin("|"))
        }
        out.print("    }\n}")


        out.close()

    }
}