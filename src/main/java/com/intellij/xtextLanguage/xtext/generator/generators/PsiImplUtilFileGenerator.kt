package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.Cardinality
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeLeaf
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeNode.Companion.filterNodesInSubtree
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
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
            |import com.intellij.psi.impl.source.tree.Factory;
            |import com.intellij.psi.impl.source.tree.LeafElement;
            |import com.intellij.psi.impl.source.tree.SharedImplUtil;
            |import com.intellij.util.CharTable;
            |import com.intellij.lang.ASTNode;
            |import java.util.Optional;
            
            |public class ${extension.capitalize()}PsiImplUtil {
        """.trimMargin("|"))
        context.rules.filterIsInstance<TreeParserRule>().filter { it.isReferenced }.forEach {
            val ruleName = it.name
            out.println("""
                        |    public static PsiElement setName($extensionCapitalized$ruleName element, String newName) {
                        |        ASTNode elementNode = element.getNode();
                        |        ASTNode oldValueElement = elementNode.findChildByType(${extensionCapitalized}Types.ID);
                        |        if(oldValueElement != null){
                        |            CharTable charTableByTree = SharedImplUtil.findCharTableByTree(element.getNode());
                        |            LeafElement newValueElement = Factory.createSingleLeafElement(${extensionCapitalized}Types.ID, newName, charTableByTree, element.getManager());
                        |            elementNode.replaceChild(oldValueElement, newValueElement);
                        |        }
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
            generateGetNameIdentifierMethodBody(out, it, "      ", "element")
            out.println("        return null;\n    }")

        }
        out.println("""
            |}    
        """.trimMargin("|"))
        out.close()

    }

    private fun generateGetNameIdentifierMethodBody(out: PrintWriter, rule: TreeParserRule, indent: String, elementName: String) {
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
                    .map { context.getRuleByName(it.getBnfName()) }
                    .filterIsInstance<TreeParserRule>()
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