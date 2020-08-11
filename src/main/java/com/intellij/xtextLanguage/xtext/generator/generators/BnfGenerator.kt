package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class BnfGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {


    fun generateBnf() {
        val file = Generator.createFile(extention.capitalize() + ".bnf", myGenDir + "/grammar")
        val out = PrintWriter(FileOutputStream(file))
        out.print("{\n")
        generateTerminalRules(out)
        generateAttributes(out)
        out.print("}\n")
        generateRules(out)
        generateEnumRules(out)
        generateReferences(out)
        out.close()
    }

    fun generateTerminalRules(out: PrintWriter) {
        out.print("    tokens = [\n")
        fileModel.terminalRules.forEach {
            out.print(it.name + "=\"regexp:")
            it.alterntiveElements.forEach {
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
          |    parserClass="$packageDir.parser.${extention.capitalize()}Parser"
        
          |    extends="$packageDir.psi.impl.${extention.capitalize()}PsiCompositeElementImpl"
          |    psiClassPrefix="${extention.capitalize()}"
          |    psiImplClassSuffix="Impl"
          |    psiPackage="$packageDir.psi"
          |    psiImplPackage="$packageDir.impl"

          |    elementTypeHolderClass="$packageDir.psi.${extention.capitalize()}Types"
          |    elementTypeClass="$packageDir.psi.${extention.capitalize()}ElementType"
          |    tokenTypeClass="$packageDir.psi.${extention.capitalize()}TokenType"
          |    parserUtilClass= "com.intellij.languageUtil.parserUtilBase.GeneratedParserUtilBaseCopy"
          |    psiImplUtilClass="$packageDir.psi.impl.${extention.capitalize()}PsiImplUtil"
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
                out.print("${extention.capitalize()}File ::= ${it.name}\n")
            }
            if (it.isPrivate) out.print("private ")
            out.print("${createGkitRuleName(it.name)} ::= ")

            it.alternativesElements.forEach {
                out.print(it.getBnfName() + " ")

            }
            out.print("\n")
            if (it.isReferenced) out.print("""
                |{
                |mixin="$packageDir.psi.impl.${extention.capitalize()}NamedElementImpl"
                |implements="com.intellij.psi.PsiNameIdentifierOwner"
                |methods=[ getName setName getNameIdentifier ]
                |}

            """.trimMargin("|"))
            out.print(it.bnfExtentionsString)
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