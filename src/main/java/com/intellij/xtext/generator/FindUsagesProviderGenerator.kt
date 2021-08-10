package com.intellij.xtext.generator

import com.intellij.xtext.metamodel.MetaContext
import com.intellij.xtext.metamodel.elements.names.NameGenerator
import com.intellij.xtext.metamodel.elements.tree.TreeParserRule
import java.io.FileOutputStream
import java.io.PrintWriter

class FindUsagesProviderGenerator(extension: String, val context: MetaContext, rootPath: String) :
    com.intellij.xtext.generator.AbstractGenerator(extension, rootPath) {
    private val referencedRules = context.rules.filterIsInstance<TreeParserRule>().filter { it.isReferenced }
    fun generateUsagesProvider() {
        val file = createOrFindFile(extensionCapitalized + "FindUsagesProvider.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.println(
            """
            |package $packageDir;
            |
            |import com.intellij.lang.HelpID;
            |import com.intellij.lang.cacheBuilder.WordsScanner;
            |import com.intellij.lang.findUsages.FindUsagesProvider;
            |import com.intellij.psi.PsiElement;
            |import com.intellij.psi.PsiNamedElement;
            |import $packageDir.psi.*;
            |import com.sun.istack.NotNull;
            |import com.sun.istack.Nullable;
            |
            |public class ${extensionCapitalized}FindUsagesProvider implements FindUsagesProvider {
            |
            |    @Nullable
            |    @Override
            |    public WordsScanner getWordsScanner() {
            |        return new ${extensionCapitalized}WordScanner();
            |    }
            |
            |    @Nullable
            |    @Override
            |    public String getHelpId(@NotNull PsiElement psiElement) {
            |        return HelpID.FIND_OTHER_USAGES;
            |    }
            |    
            |    @NotNull
            |    @Override
            |    public String getDescriptiveName(@NotNull PsiElement element) {
            |        if (element instanceof PsiNamedElement) {
            |            return ((PsiNamedElement) element).getName();
            |        } else {
            |            return "";
            |        }
            |     }
            |     
            |     @NotNull
            |     @Override
            |     public String getNodeText(@NotNull PsiElement element, boolean useFullName) {
            |         if (element instanceof PsiNamedElement) {
            |             return element.getText();
            |         } else {
            |             return "";
            |         }
            |     }
            |    
        
        """.trimMargin("|"))
        generateCanFindUsagesForMethod(out)
        generateGetTypeMethod(out)
        out.println("}")
        out.close()
    }


    private fun generateCanFindUsagesForMethod(out: PrintWriter) {
        val returnString = referencedRules
                .map { "$extensionCapitalized${NameGenerator.toGKitClassName(it.name)}" }
                .map { "psiElement instanceof $it" }
                .joinToString(separator = " ||\n         ")
        out.println("""
            |    @Override
            |    public boolean canFindUsagesFor(@NotNull PsiElement psiElement) {
            |        return $returnString;
            |    }
        """.trimMargin("|"))
    }

    private fun generateGetTypeMethod(out: PrintWriter) {
        val returnString = referencedRules
                .map { "$extensionCapitalized${NameGenerator.toGKitClassName(it.name)}" }
                .map { "if (element instanceof $it) {\n          return \"${it}\";\n      }" }
                .joinToString(separator = " else ")
        out.println("""
            |    @NotNull
            |    @Override
            |    public String getType(@NotNull PsiElement element) {
            |        $returnString else {
            |            return "";
            |        }
            |    }
        """.trimMargin("|"))
    }

}