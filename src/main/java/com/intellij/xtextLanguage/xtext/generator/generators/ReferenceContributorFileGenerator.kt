package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserCrossReferenceElement
import java.io.FileOutputStream
import java.io.PrintWriter

class ReferenceContributorFileGenerator(extension: String, val references: List<ParserCrossReferenceElement>) : AbstractGenerator(extension) {
    fun generateReferenceContributorFile() {
        val file = createFile(extension.capitalize() + "ReferenceContributor.java", myGenDir)
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
            
            |public class ${extension.capitalize()}ReferenceContributor extends PsiReferenceContributor {
            |    @Override
            |    public void registerReferenceProviders(@NotNull PsiReferenceRegistrar registrar) {
        """.trimMargin("|"))
        references.distinctBy { it.name }.forEach {
            val referenceName = it.name.replace("_", "")
            out.print("""
            |        registrar.registerReferenceProvider(PlatformPatterns.psiElement(${extension.capitalize()}${referenceName}.class).withLanguage(${extension.capitalize()}Language.INSTANCE),
            |                new PsiReferenceProvider() {
            |            @NotNull
            |            @Override
            |            public PsiReference[] getReferencesByElement(@NotNull PsiElement element,
            |                                                         @NotNull ProcessingContext context){
            |                ${extension.capitalize()}${referenceName} reference = (${extension.capitalize()}${referenceName}) element;
            |                String value = reference.getText();
            |                ArrayList<Class<? extends PsiNameIdentifierOwner>> list = new ArrayList<>((Collection<? extends Class<? extends PsiNameIdentifierOwner>>)Arrays.asList(
            """.trimMargin("|"))
            val targets = it.targets
            targets.forEach {
                out.print("${extension.capitalize()}${it.superRuleName}.class")
                if (it !== targets.last()) out.print(", ")
            }
            out.print("));\n")
            out.println("""
            |                return new PsiReference[]{
            |                    new ${extension.capitalize()}Reference(element, new TextRange(0, value.length()), list)};
            |                }
            |        });
            """.trimMargin("|"))
        }
        out.print("    }\n}")


        out.close()

    }
}