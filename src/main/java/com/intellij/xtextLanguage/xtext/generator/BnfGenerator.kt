package com.intellij.xtextLanguage.xtext.generator

import com.intellij.psi.util.PsiTreeUtil
import com.intellij.xtextLanguage.xtext.psi.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintWriter

class BnfGenerator(val extention: String, val fileModel: XtextFileModel) {
    internal val myGenDir = "src/main/java/com/intellij/xtextLanguage/xtext/grammar/"
    internal val myBnfFile = File("$myGenDir$extention.bnf")
    internal var out = PrintWriter(FileOutputStream(myBnfFile))

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


        generateTerminalRules()
        generateAttributes()
        generateRules()
//        generateEnumRules()
        generateReferences()

        out.close()
    }

    private fun generateReferences() {
        fileModel.myReferences.forEach { out.print("${it.name} ::= ${it.referenceType}\n") }
    }

    private fun generateEnumRules() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun generateRules() {
        fileModel.myParserRules.forEach { out.print("${it.name} ::= ${getRuleAlternativesAsString(it.myRule)}\n") }
    }

    private fun generateAttributes() {
        out.print("""
            
  parserClass="com.intellij.xtextLanguage.xtext.parser.XtextParser"

  extends="com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiCompositeElementImpl"
  psiClassPrefix="Xtext"
  psiImplClassSuffix="Impl"
  psiPackage="com.intellij.xtextLanguage.xtext.psi"
  psiImplPackage="com.intellij.xtextLanguage.xtext.impl"

  elementTypeHolderClass="com.intellij.xtextLanguage.xtext.psi.XtextTypes"
  elementTypeClass="com.intellij.xtextLanguage.xtext.psi.XtextElementType"
  tokenTypeClass="com.intellij.xtextLanguage.xtext.psi.XtextTokenType"
  psiImplUtilClass="com.intellij.xtextLanguage.xtext.psi.impl.XtextPsiImplUtil"
  parserUtilClass= "com.intellij.xtextLanguage.xtext.parserUtilBase.GeneratedParserUtilBaseCopy"
  generateTokenAccessors=true
  generateTokens=true

}

        """.trimIndent())
    }

    private fun generateTerminalRules() {
        out.print("""{
            |   tokens = [
        """.trimMargin())
        fileModel.myTerminalRules.forEach { out.print("      ${it.name} =\"regexp: ${getRegexpAsString(it.myRule)}\"\n") }
        out.print("    ]")
    }

    fun getRuleAlternativesAsString(rule: XtextParserRule?): String {
        val sb = StringBuilder()
        if (rule == null) return ""
        val branches = rule.alternatives?.conditionalBranchList
        if (branches == null) return ""
        for (b in branches) {
            writebrunch(b, sb)
            sb.append("${if (b != branches.last()) {
                "| "
            } else {
                ""
            }} ")
        }
        return sb.toString()
    }

    private fun writebrunch(b: XtextConditionalBranch, sb: StringBuilder): String {
        if (b == null) return ""
        val unorderedGroup = b.getUnorderedGroup() ?: return ""
        val groups = unorderedGroup.getGroupList()
        val tokens = ArrayList<XtextAbstractToken>()
        groups.forEach { tokens.addAll(it.abstractTokenList) }
        val abstractTokens = ArrayList<XtextAbstractTokenWithCardinality>()
        tokens.forEach { abstractTokens.add(it.abstractTokenWithCardinality ?: return@forEach) }
        if (abstractTokens.size == 0) return ""
//        abstractTokens.forEach {if (it.abstractTerminal != null)writeAbstractTerminal(it.abstractTerminal!!, sb)
//                                else if(it.assignment != null)writeAssignableTerminal(it.assignment!!.assignableTerminal, sb)
//        }
        abstractTokens.forEach {
            it.abstractTerminal?.let { writeAbstractTerminal(it, sb) }
            it.assignment?.assignableTerminal?.let { writeAssignableTerminal(it, sb) }
            it.asterisk?.let { sb.append("${it.text} ") }
            it.plus?.let { sb.append("${it.text} ") }
            it.quesMark?.let { sb.append("${it.text} ") }
        }
        return sb.toString()
    }


    private fun writeAbstractTerminal(abstractTerminal: XtextAbstractTerminal, sb: StringBuilder): String {
        val keyword = abstractTerminal.keyword
        val ruleCall = abstractTerminal.ruleCall
        val parenthesizedElement = abstractTerminal.parenthesizedElement
        val pKeyword = abstractTerminal.predicatedKeyword
        val pRuleCall = abstractTerminal.predicatedRuleCall
        val pParenthesizedElement = abstractTerminal.predicatedGroup
        keyword?.let { sb.append("${it.text} ") }
        ruleCall?.let { sb.append("${it.referenceAbstractRuleRuleID.text} ") }
        parenthesizedElement?.let {
            val branches = it.alternatives.conditionalBranchList
            sb.append("(")
            branches.forEach {
                writebrunch(it, sb)
                sb.append("${if (it != branches.last()) {
                    "| "
                } else {
                    ""
                }} ")
            }
            sb.append(")")
        }
        pKeyword?.let { sb.append("${it.text} ") }
        pRuleCall?.let { sb.append("${it.referenceAbstractRuleRuleID.text} ") }
        pParenthesizedElement?.let {
            val branches = it.alternatives.conditionalBranchList
            sb.append("(")
            branches.forEach {
                writebrunch(it, sb)
                sb.append("${if (it != branches.last()) {
                    "| "
                } else {
                    ""
                }} ")
            }

            sb.append(") ")
        }
        return sb.toString()
    }


    private fun writeAssignableTerminal(assignableTerminal: XtextAssignableTerminal, sb: StringBuilder): String {
        val keyword = assignableTerminal.keyword
        val ruleCall = assignableTerminal.ruleCall
        val parenthesizedAssignableElement = assignableTerminal.parenthesizedAssignableElement
        val crossReference = assignableTerminal.crossReference
        keyword?.let { sb.append("${it.text} ") }
        ruleCall?.let { sb.append("${it.referenceAbstractRuleRuleID.text} ") }
        parenthesizedAssignableElement?.let {
            val branches = it.assignableAlternatives.assignableTerminalList
            sb.append("(")
            branches.forEach {
                writeAssignableTerminal(it, sb)
                sb.append("${if (it != branches.last()) {
                    "| "
                } else {
                    ""
                }} ")
            }
            sb.append(") ")
        }
        crossReference?.let {
            sb.append("REFERENCE_${it.typeRef.text}")
            it.crossReferenceableTerminal?.let { sb.append("_${it.text}") }
            sb.append(" ")
        }
        return sb.toString()
    }

    fun getRegexpAsString(rule: XtextTerminalRule): String {
        rule ?: return ""
        val sb = StringBuilder()
        var leaf = PsiTreeUtil.nextLeaf(rule.colon!!)
        loop@ while (leaf != null && PsiTreeUtil.isAncestor(rule, leaf, true)) {
            val type = leaf.node.elementType.toString()
            val stringBuilder = when (type) {
                "STRING" -> {
                    var str = leaf.text.replace("\'", "")
                    str = avoidRegexpSymbols(str)
                    sb.append(str)
                }
                "->" -> sb.append("(?s).*")
                ".." -> {
                    val ch = sb.get(sb.length - 1)
                    sb.deleteCharAt(sb.length - 1)
                    sb.append("[$ch-")

                    leaf = PsiTreeUtil.nextLeaf(leaf)
                    if (leaf != null) {
                        if (leaf.getNode()?.elementType.toString() == "WHITE_SPACE") {
                            leaf = PsiTreeUtil.nextLeaf(leaf)
                        }
                        if (leaf?.getNode()?.elementType.toString() == "STRING") {
                            var str = leaf?.text?.replace("\'", "")
                            str = avoidRegexpSymbols(str ?: "")
                            sb.append("$str]")
                        } else break@loop
                    } else break@loop

                }
                "ID" -> sb.append("ref_${leaf.text}")
                "!" -> {
                    sb.clear()
                    break@loop
                }
                ";" -> break@loop
                else -> sb.append("")
            }
            if (leaf != null) {
                leaf = PsiTreeUtil.nextLeaf(leaf)
            }
        }
        return sb.toString()
    }

    private fun avoidRegexpSymbols(string: String): String {
        var sb = StringBuilder()
        val bannedSymbols = arrayOf('*', '.', '"')
        string.toCharArray().forEach {
            if (it in bannedSymbols) sb.append("\\$it")
            else sb.append(it)
        }
        return sb.toString()
    }
}


