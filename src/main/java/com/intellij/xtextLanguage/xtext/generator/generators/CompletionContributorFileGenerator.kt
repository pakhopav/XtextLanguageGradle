package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class CompletionContributorFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateCompletionContributorFile() {
        val file = createFile(extention.capitalize() + "CompletionContributor.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.codeInsight.completion.CompletionContributor;
            |import com.intellij.codeInsight.completion.CompletionType;
            |import com.intellij.languageUtil.completion.KeywordCompletionProvider;
            |import $packageDir.psi.${extention.capitalize()}TokenType;
            |import $packageDir.psi.${extention.capitalize()}File;
            
            |import static com.intellij.patterns.PlatformPatterns.psiElement;
            
            
            |public class ${extention.capitalize()}CompletionContributor extends CompletionContributor {
            |public ${extention.capitalize()}CompletionContributor() {
            
            |extend(CompletionType.BASIC, psiElement().withLanguage(${extention.capitalize()}Language.INSTANCE)
            |,
            |new KeywordCompletionProvider<${extention.capitalize()}File, ${extention.capitalize()}TokenType>(${extention.capitalize()}Language.INSTANCE, ${extention.capitalize()}FileType.INSTANCE, ${extention.capitalize()}TokenType.class));
            
            |}
            |}


        """.trimMargin("|"))
        out.close()

    }
}