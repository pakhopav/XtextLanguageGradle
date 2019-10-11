package com.intellij.xtextLanguage.xtext.generator

import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter

class VisitorGenerator(val model: VisitorGeneratorModel, val extention: String) {
    val myGenDir = "src/main/java/com/intellij/${extention.toLowerCase()}Language/${extention.toLowerCase()}"
    val packageDir = "com.intellij.${extention.toLowerCase()}Language.${extention.toLowerCase()}"

    fun generateVisitor() {

    }

    fun generateNameVisitor() {
        val path = File(myGenDir + "/psi")
        val file = File(myGenDir + "/psi/${extention}NameVisitor.kt")
        path.mkdirs()
        file.createNewFile()
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi
            
            |import $packageDir.psi.*
            |import com.intellij.psi.PsiElement
            
            |class ${extention}NameVisitor {
        """.trimMargin("|"))
        out.print("\n")
        model.rules.forEach {
            val methodName = "visit${model.getXtextRuleName(it)}"
            out.print("    fun $methodName (node : ${extention}${model.getXtextRuleName(it)}): PsiElement?{\n")
            model.getUniqueName(it)?.let {
                out.print("        return node.${it.decapitalize()}\n")
                out.print("        return null\n")
                out.print("    }\n")
                return@forEach
            }
            if (model.hasUniqueId(it)) {
                out.print("        return node.id\n")
                out.print("        return null\n")

                out.print("    }\n")
                return@forEach
            }

            if (model.hasId(it)) {
                out.print("        node.id?.let {return@$methodName it}\n")
            }
            model.getRulesRuleCalls(it).forEach {

                out.print("        node.${it.decapitalize()}?.let {visit${it}(it)?.let {return@$methodName it}}\n")


            }
            model.getRulesRuleCallsList(it).forEach {
                out.print("        node.${it.decapitalize()}List.forEach{visit${it}(it)?.let {return@$methodName it }}\n")
            }
            out.print("        return null\n")

            out.print("    }\n")

        }
        out.print("}")
        out.close()
    }
}