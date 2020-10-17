package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeCrossReference
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import java.io.FileOutputStream
import java.io.PrintWriter

class ReferenceContributorFileGenerator(extension: String, val context: MetaContext) : AbstractGenerator(extension) {
    private val relevantRules = context.rules.filterIsInstance<TreeParserRule>()
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
        val referenceNodes = relevantRules.flatMap { it.filterNodesInSubtree { it is TreeCrossReference } }.map { it as TreeCrossReference }
        referenceNodes.distinctBy { it.getBnfName() }.forEach { crossReferenceNode ->
            val targetRuleNames = relevantRules.filter { it.returnType == crossReferenceNode.targetType }.map { it.name }
            val referenceName = crossReferenceNode.getBnfName().replace("_", "")
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
            out.print(targetRuleNames.map { "${extension.capitalize()}$it.class" }.joinToString())
            out.println("));")
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