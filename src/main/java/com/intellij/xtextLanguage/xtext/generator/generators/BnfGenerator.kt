package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class BnfGenerator(extension: String, val fileModel: XtextMainModel) : AbstractGenerator(extension) {


    fun generateBnf() {
        val file = MainGenerator.createFile(extension.capitalize() + ".bnf", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("{\n")
        generateTerminalRules(out)
        generateAttributes(out)
        out.print("}\n")
        generateRules(out)
        generateEnumRules(out)
//        generateReferences(out)
        out.close()
    }

    fun generateTerminalRules(out: PrintWriter) {
        out.print("    tokens = [\n")
        fileModel.terminalRules.forEach {
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
        fileModel.keywordModel.keywords.forEach { out.print("      ${it.name} = ${it.keyword}\n") }
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

    private fun generateReferences(out: PrintWriter) {
        fileModel.referencesModel.references.forEach { out.print("${it.name} ::= ${it.referenceType}\n") }
    }

    private fun generateEnumRules(out: PrintWriter) {
        fileModel.enumRules.forEach {
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
        fileModel.parserRules.forEach {

            if (it == fileModel.parserRules.first()) {
                out.print("${extension.capitalize()}File ::= ${it.name}\n")
            }
            if (it.isPrivate) out.print("private ")
            out.print("${createGkitRuleName(it.name)} ::= ")

            it.alternativeElements.forEach {
                out.print(it.getBnfName() + " ")

            }
            out.print("\n")
            if (it.isReferenced) out.print("""
                |{
                |mixin="$packageDir.psi.impl.${extension.capitalize()}NamedElementImpl"
                |implements="com.intellij.psi.PsiNameIdentifierOwner"
                |methods=[ getName setName getNameIdentifier ]
                |}

            """.trimMargin("|"))
            out.print(it.bnfExtensionsString)
            out.print("\n")
        }
    }

    fun createGkitRuleName(oldName: String?): String {
        var newName: String? = null
        oldName?.let {
            if (it.startsWith("^")) {
                newName = it.replace("^", "Caret").capitalize()
            } else {
                newName = it.capitalize()
            }
        }

        return newName ?: ""
    }
}