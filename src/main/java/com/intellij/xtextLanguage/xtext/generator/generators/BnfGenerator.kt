package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.tree.TreeRoot
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
        generateEnumRules(out)
        out.close()
    }

    fun generateTerminalRules(out: PrintWriter) {
        out.print("    tokens = [\n")
        context.terminalRules.forEach {
            out.print(it.name.toUpperCase() + "=\"regexp:")
            it.alternativeElements.forEach {
                out.print(it.getBnfName())
            }
            out.print("\"\n")

        }
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


    private fun generateEnumRules(out: PrintWriter) {
        context.enumRules.forEach {
            val rule = it
            out.print("${it.name} ::= ")
            it.alternativeElements.forEach {
                out.print(it.getBnfName())
                if (it != rule.alternativeElements.last()) {
                    out.print(" | ")
                }
            }
            out.print("\n")

        }

    }

    private fun generateRules(out: PrintWriter) {
        out.print("${extension.capitalize()}File ::= ${context.parserRules[0].name}\n")
        context.parserRules.forEach {
            if (context.hasPrivateDuplicate(it)) {
                generatePrivateDuplicate(it, out)
            }
            if (it.isFragment) out.print("private ")
            out.print("${it.name} ::= ${it.getBnfString()}\n")
            val bnfExtensions = mutableListOf<String>()
            if (context.isReferencedRule(it)) {
                bnfExtensions.add("""
                mixin="$packageDir.psi.impl.${extension.capitalize()}NamedElementImpl"
                implements="com.intellij.psi.PsiNameIdentifierOwner"
                methods=[ getName setName getNameIdentifier ]
                """.trimIndent())
            }
            it.superRuleName?.let {
                bnfExtensions.add("extends=$it")
            }
            if (bnfExtensions.isNotEmpty()) {
                out.println("{")
                bnfExtensions.forEach {
                    out.println(it)
                }
                out.println("}")
            }
            out.print("\n")

        }
    }


    private fun generatePrivateDuplicate(rule: TreeRoot, out: PrintWriter) {
        val newName = rule.name + "Private"
        val body = rule.children.map { it.getBnfString() }.joinToString(separator = " ")
        out.println("private $newName ::= $body\n")
    }
}