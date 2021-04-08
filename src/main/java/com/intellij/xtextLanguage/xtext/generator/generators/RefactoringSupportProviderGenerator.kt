package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import java.io.FileOutputStream
import java.io.PrintWriter

class RefactoringSupportProviderGenerator(extension: String, val context: MetaContext, rootPath: String) :
    AbstractGenerator(extension, rootPath) {
    fun generateRefactoringSupportProvider() {
        val file = createFile("${extensionCapitalized}RefactoringSupportProvider.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        val referencedRules = context.rules.filterIsInstance<TreeParserRule>()
            .filter { it.isReferenced }
        val returnString = referencedRules.map { "$extensionCapitalized${it.name}" }
            .joinToString(
                prefix = "elementToRename instanceof ",
                separator = " ||\n                elementToRename instanceof "
            )

        out.print(
            """
        |package com.intellij.${extension}Language.${extension};
        |
        |import com.intellij.${extension}Language.${extension}.psi.*;
        |import com.intellij.lang.refactoring.RefactoringSupportProvider;
        |import com.intellij.psi.PsiElement;
        |import org.jetbrains.annotations.NotNull;
        |import org.jetbrains.annotations.Nullable;
        |
        |public class ${extensionCapitalized}RefactoringSupportProvider extends RefactoringSupportProvider {
        |
        |    @Override
        |    public boolean isMemberInplaceRenameAvailable(@NotNull PsiElement elementToRename, @Nullable PsiElement context) {
        |        return  $returnString;
        |    }
        |
        |}
        """.trimMargin("|"))

        out.close()

    }
}