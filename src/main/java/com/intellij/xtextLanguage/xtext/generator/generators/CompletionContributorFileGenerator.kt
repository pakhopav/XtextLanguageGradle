package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class CompletionContributorFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateCompletionContributorFile() {
        val file = createFile(extention + "CompletionContributor.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.codeInsight.completion.CompletionContributor;
            |import com.intellij.codeInsight.completion.CompletionType;
            |import com.intellij.languageUtil.completion.KeywordCompletionProvider;
            |import $packageDir.psi.${extention}TokenType;
            |import $packageDir.psi.${extention}File;
            
            |import static com.intellij.patterns.PlatformPatterns.psiElement;
            
            
            |public class ${extention}CompletionContributor extends CompletionContributor {
            |public ${extention}CompletionContributor() {
            
            |extend(CompletionType.BASIC, psiElement().withLanguage(${extention}Language.INSTANCE)
            |,
            |new KeywordCompletionProvider<${extention}File, ${extention}TokenType>(${extention}Language.INSTANCE, ${extention}FileType.INSTANCE, ${extention}TokenType.class));
            
            |}
            |}


        """.trimMargin("|"))
        out.close()

    }
}