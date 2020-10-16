package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRoot
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRuleCall
import java.io.FileOutputStream
import java.io.PrintWriter

class PsiImplUtilFileGenerator(extension: String, val context: MetaContext) : AbstractGenerator(extension) {
    fun geneneratePsiImplUtilFile() {
        val file = createFile(extension.capitalize() + "PsiImplUtil.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.println("""
             |package $packageDir.psi.impl;
                        
            |import com.intellij.psi.*;
            |import $packageDir.psi.*;
            |import com.intellij.psi.util.PsiTreeUtil;
            
            |import java.util.Optional;
            
            |public class ${extension.capitalize()}PsiImplUtil {
        """.trimMargin("|"))
        context.parserRules.filter { context.isReferencedRule(it) }.forEach {
            val ruleName = it.name
            out.println("""
                        |    public static PsiElement setName($extensionCapitalized$ruleName element, String newName) {
                        |        //TODO
                        |        return element;
                        |    }
                        
                        |    public static String getName($extensionCapitalized$ruleName element) {
                        |        return Optional.ofNullable(getNameIdentifier(element))
                        |            .map(PsiElement::getText)
                        |            .orElse(null);
                        |    }
                        |    
                        |    public static PsiElement getNameIdentifier($extensionCapitalized$ruleName element) {
                    """.trimMargin("|"))
            generateGetNameIdentifierMethodBody(out, it, "            ", "element")
            out.println("            return null;\n        }")

        }
        out.println("""
            |}    
        """.trimMargin("|"))
        out.close()

    }

    private fun generateGetNameIdentifierMethodBody(out: PrintWriter, rule: TreeRoot, indent: String, elementName: String) {
        var optionalName = true
        if (rule.hasNameFeature()) {
            val nodesWithName = rule.filterNodesInSubtree { it is TreeLeaf && it.assignment != null && it.assignment!!.featureName == "name" }
                    .map { it as TreeLeaf }
                    .distinctBy { it.getBnfName() }
            nodesWithName.forEach {
                if (it.cardinality == Cardinality.NONE) optionalName = false
                val getMethodName = "get${NameGenerator.toGKitClassName(it.getBnfName())}"
                out.println("${indent}if($elementName.$getMethodName() != null){")
                out.println("${indent}    return $elementName.$getMethodName();\n${indent}}")
            }
        }
        if (optionalName) {
            val rulesCalledWithoutAssignment = rule
                    .filterNodesInSubtree { it is TreeRuleCall && it.assignment == null }
                    .map { it as TreeRuleCall }
                    .map { context.getParserRuleByName(it.getBnfName()) }
                    .filterNotNull()
            rulesCalledWithoutAssignment.filter { it.superRuleName == rule.name }.forEach {
                val calledRuleName = NameGenerator.toGKitClassName(it.name)
                val calledRuleClassName = "$extensionCapitalized$calledRuleName"
                out.println("${indent}if($elementName  instanceof $calledRuleClassName){")
                out.println("${indent}    $calledRuleClassName ${calledRuleName.decapitalize()} = ($calledRuleClassName) $elementName;")
                generateGetNameIdentifierMethodBody(out, it, "    $indent", calledRuleName.decapitalize())
                out.println("$indent}")

            }
        }

    }
}