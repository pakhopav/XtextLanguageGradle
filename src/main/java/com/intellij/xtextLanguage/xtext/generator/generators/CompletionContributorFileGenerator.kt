package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class CompletionContributorFileGenerator(extension: String) : AbstractGenerator(extension) {
    fun generateCompletionContributorFile() {
        val file = createFile(extension.capitalize() + "CompletionContributor.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.codeInsight.completion.CompletionContributor;
            |import com.intellij.codeInsight.completion.CompletionType;
            |import com.intellij.languageUtil.completion.KeywordCompletionProvider;
            |import $packageDir.psi.${extension.capitalize()}TokenType;
            |import $packageDir.psi.${extension.capitalize()}File;
            
            |import static com.intellij.patterns.PlatformPatterns.psiElement;
            
            
            |public class ${extension.capitalize()}CompletionContributor extends CompletionContributor {
            |public ${extension.capitalize()}CompletionContributor() {
            |    extend(CompletionType.BASIC, psiElement().withLanguage(${extension.capitalize()}Language.INSTANCE),
            |        new KeywordCompletionProvider<${extension.capitalize()}File, ${extension.capitalize()}TokenType>(${extension.capitalize()}Language.INSTANCE, ${extension.capitalize()}FileType.INSTANCE, ${extension.capitalize()}TokenType.class));
            |    }
            |}


        """.trimMargin("|"))
        out.close()

    }
}