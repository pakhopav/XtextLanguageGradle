package com.intellij.xtextLanguage.xtext.generator

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintWriter

class BnfGenerator(val extention: String, val fileModel: XtextFileModel) {
    internal val myGenDir = "src/main/java/com/intellij/xtextLanguage/xtext/grammar/"
    internal val myTestGenDir = "src/test/resources/testData/generation/generateBnf/"

    internal val myBnfFile = File("$myGenDir$extention.bnf")
    internal var out = PrintWriter(FileOutputStream(myBnfFile))
    val generatorUtil = BnfGeneratorUtil(fileModel)

    init {
        try {
            myBnfFile.createNewFile()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @Throws(IOException::class)
    fun generate() {

        out = PrintWriter(FileOutputStream(myBnfFile))
        out.print("{\n")

        generateTerminalRules()
        generateAttributes()

        out.print("}\n")

        generateRules()
        generateEnumRules()
        generateReferences()

        out.close()
    }

    private fun generateTerminalRules() {
        out.print("    tokens = [\n")
        fileModel.myTerminalRules.forEach {
            if (generatorUtil.getRegexpAsString(it.myRule) != "") {
                out.print("      ${it.name} =\"regexp:${generatorUtil.getRegexpAsString(it.myRule)}\"\n")
            }

        }
        out.print("    ]\n")
    }

    private fun generateAttributes() {
        out.print("""
          |    parserClass="com.intellij.xtextLanguage.xtext.parser.XtextParser"
        
          |    extends="com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl"
          |    psiClassPrefix="Xtext"
          |    psiImplClassSuffix="Impl"
          |    psiPackage="com.intellij.xtextLanguage.xtext.psi"
          |    psiImplPackage="com.intellij.xtextLanguage.xtext.impl"

          |    elementTypeHolderClass="com.intellij.xtextLanguage.xtext.psi.XtextTypes"
          |    elementTypeClass="com.intellij.xtextLanguage.xtext.psi.XtextElementType"
          |    tokenTypeClass="com.intellij.xtextLanguage.xtext.psi.XtextTokenType"
          |    psiImplUtilClass="com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiImplUtil"
          |    parserUtilClass= "com.intellij.xtextLanguage.xtext.parserUtilBase.GeneratedParserUtilBaseCopy"
          |    generateTokenAccessors=true
          |    generateTokens=true
                """.trimMargin("|"))
        out.print("\n")
    }

    private fun generateReferences() {
        fileModel.myReferences.forEach { out.print("${it.name} ::= ${it.referenceType}\n") }
    }

    private fun generateEnumRules() {
        fileModel.myEnumRules.forEach { out.print("${it.name} ::= ${generatorUtil.getEnumRuleDeclarationsAsString(it.myRule)}\n") }

    }

    private fun generateRules() {
        fileModel.myParserRules.forEach { out.print("${generatorUtil.nameWithCaret(it.name)} ::= ${generatorUtil.getRuleAlternativesAsString(it.myRule)}\n") }
    }


}


