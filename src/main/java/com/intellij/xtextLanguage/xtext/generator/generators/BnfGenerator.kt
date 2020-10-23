package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeParserRule
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRule
import java.io.FileOutputStream
import java.io.PrintWriter

class BnfGenerator(extension: String, val context: MetaContext) : AbstractGenerator(extension) {
    fun generateBnf() {
        val file = MainGenerator.createFile(extension.capitalize() + ".bnf", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("{\n")
        generateTerminalRules(out)
        generateAttributes(out)
        out.print("}\n")
        generateRules(out)
//        generateEnumRules(out)
        out.close()
    }

    fun generateTerminalRules(out: PrintWriter) {
        out.print("    tokens = [\n")
        context.terminalRs.forEach {
            out.println("${it.name.toUpperCase()} = \"${it.name}\"")
        }
//        context.terminalRules.forEach {
//            out.print(it.name.toUpperCase() + "=\"regexp:")
//            it.alternativeElements.forEach {
//                out.print(it.getBnfName())
//            }
//            out.print("\"\n")
//
//        }
        generateKeywordTokens(out)
        out.print("    ]\n")
    }

    private fun generateKeywordTokens(out: PrintWriter) {
        context.keywordModel.keywords.forEach { out.print("      ${it.name} = \'${it.keyword}\'\n") }
    }

    private fun generateAttributes(out: PrintWriter) {
        out.print("""
          |    parserClass="$packageDir.parser.${extension.capitalize()}Parser"
        
          |    extends="$packageDir.psi.impl.${extension.capitalize()}PsiCompositeElementImpl"
          |    psiClassPrefix="${extension.capitalize()}"
          |    psiImplClassSuffix="Impl"
          |    psiPackage="$packageDir.psi"
          |    psiImplPackage="$packageDir.impl"
          |    elementTypeHolderClass="$packageDir.psi.${extension.capitalize()}Types"
          |    elementTypeClass="$packageDir.psi.${extension.capitalize()}ElementType"
          |    tokenTypeClass="$packageDir.psi.${extension.capitalize()}TokenType"
          |    parserUtilClass= "com.intellij.languageUtil.parserUtilBase.GeneratedParserUtilBaseCopy"
          |    psiImplUtilClass="$packageDir.psi.impl.${extension.capitalize()}PsiImplUtil"
          |    generateTokenAccessors=true
          |    generateTokens=true
          |    extraRoot(".*")= true
                """.trimMargin("|"))
        out.print("\n")
    }



    private fun generateRules(out: PrintWriter) {
        out.print("${extension.capitalize()}File ::= ${context.rules[0].name}\n")
        context.rules.forEach { rule ->
            out.println(rule.getString())
            val bnfExtensions = mutableListOf<String>()
            if (rule is TreeParserRule) {
                if (rule.isReferenced) {
                    bnfExtensions.add("""
                mixin="$packageDir.psi.impl.${extension.capitalize()}NamedElementImpl"
                implements="com.intellij.psi.PsiNameIdentifierOwner"
                methods=[ getName setName getNameIdentifier ]
                """.trimIndent())
                }
                rule.superRuleName?.let {
                    bnfExtensions.add("extends=$it")
                }
                if ((rule as? TreeParserRule)?.isSuffix == true) {
                    bnfExtensions.add("implements=\"com.intellij.xtextLanguage.xtext.psi.SuffixElement\"")
                }
                if (bnfExtensions.isNotEmpty()) {
                    out.println("{")
                    bnfExtensions.forEach {
                        out.println(it)
                    }
                    out.println("}")
                }
            }
            out.print("\n")

        }
    }


    private fun generatePrivateDuplicate(rule: TreeRule, out: PrintWriter) {
        val newName = rule.name + "Private"
        val body = rule.children.map { it.getString() }.joinToString(separator = " ")
        out.println("private $newName ::= $body\n")
    }
}