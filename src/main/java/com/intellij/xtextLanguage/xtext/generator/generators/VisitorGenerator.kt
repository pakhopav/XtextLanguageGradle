package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.File
import java.io.FileOutputStream
import java.io.PrintWriter

class VisitorGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    val model = fileModel.visitorGeneratorModel
    fun generateVisitor() {

    }

    fun generateNameVisitor() {
        val path = File(myGenDir + "/psi")
        val file = File(myGenDir + "/psi/${extention.capitalize()}NameVisitor.kt")
        path.mkdirs()
        file.createNewFile()
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi
            
            |import $packageDir.psi.*
            |import com.intellij.psi.PsiElement
            
            |class ${extention.capitalize()}NameVisitor {
        """.trimMargin("|"))
        out.print("\n")
        model.rules.forEach {
            val rule = it
            val methodName = "visit${it.name}"
            out.print("    fun $methodName (node : ${extention.capitalize()}${it.name}): PsiElement?{\n")
            it.uniqueName?.let {
                if (rule.ruleCallsList.contains(it)) {
                    if (it == "ID") {
                        out.print("        node.idList.forEach{return@$methodName it }\n")

                    } else {
                        out.print("        node.${it.decapitalize()}List.forEach{visit${it}(it)?.let {return@$methodName it }}\n")

                    }
                    out.print("        return null\n")
                } else {
                    if (it == "ID") {
                        out.print("        return node.id\n")

                    } else {
                        out.print("        return node.${it.decapitalize()}\n")

                    }
                }


                out.print("    }\n")
                return@forEach
            }


            it.ruleCalls.forEach {
                if (it == "ID") {
                    out.print("        node.id?.let {return@$methodName it}\n")
                } else {
                    out.print("        node.${it.decapitalize()}?.let {visit${it}(it)?.let {return@$methodName it}}\n")

                }


            }
            it.ruleCallsList.forEach {
                if (it == "ID") {
                    out.print("        node.idList.forEach{return@$methodName it }\n")

                } else {
                    out.print("        node.${it.decapitalize()}List.forEach{visit${it}(it)?.let {return@$methodName it }}\n")
                }
            }
            out.print("        return null\n")

            out.print("    }\n")

        }
        out.print("}")
        out.close()
    }
}