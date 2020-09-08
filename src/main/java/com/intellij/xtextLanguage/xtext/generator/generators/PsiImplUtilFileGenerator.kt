package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserCrossReferenceElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.RuleElement
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import java.io.FileOutputStream
import java.io.PrintWriter

class PsiImplUtilFileGenerator(extension: String, val rules: List<ParserRule>, val references: List<ParserCrossReferenceElement>) : AbstractGenerator(extension) {
    private val capitalizedExtension = extension.capitalize()
    private val nameGenerator = NameGenerator()
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
        references.distinctBy { it.name }.flatMap { it.targets }.forEach { target ->
            var targetHasName = false
            val targetName = target.superRuleName
            val targetParserRule = rules.firstOrNull { it.name == targetName }
            out.println("""
                        |    public static PsiElement setName($capitalizedExtension$targetName element, String newName) {
                        |        //TODO
                        |        return element;
                        |    }
                        
                        |    public static String getName($capitalizedExtension$targetName element) {
                        |        return Optional.ofNullable(getNameIdentifier(element))
                        |            .map(PsiElement::getText)
                        |            .orElse(null);
                        |    }
                        |    
                        |    public static PsiElement getNameIdentifier($capitalizedExtension$targetName element) {
                    """.trimMargin("|"))
            val targetsNameElement = getElementAssignedToName(targetParserRule!!)
            targetsNameElement?.let {
                val elementName = nameGenerator.toGKitClassName(it.getBnfName())
                out.println("        return element.get${elementName}();")
                targetHasName = true
            }
            target.subRuleNames.forEach { subRuleName ->
                out.println("        if(element instanceof $capitalizedExtension$subRuleName){")
                val subRule = rules.firstOrNull { it.name == subRuleName }
                val nameElement = getElementAssignedToName(subRule!!)
                val elementName = nameGenerator.toGKitClassName(nameElement!!.getBnfName())
                out.println("            return (($capitalizedExtension$subRuleName)element).get${elementName}();\n        }")
            }
            out.println("""
                        |        ${if (targetHasName) "" else "return null;"}
                        |    }                        
                    """.trimMargin("|"))
        }

        out.println("""
            |}    
        """.trimMargin("|"))
        out.close()

    }

    private fun getElementAssignedToName(rule: ParserRule): RuleElement? {
        return rule.alternativeElements.firstOrNull { it.assignment == "name=" }
    }
}