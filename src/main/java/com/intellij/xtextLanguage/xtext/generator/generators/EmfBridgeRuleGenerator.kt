package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import com.intellij.xtextLanguage.xtext.generator.models.elements.ParserRule
import java.io.FileOutputStream
import java.io.PrintWriter

class EmfBridgeRuleGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    private val capitalizedExtention = extention.capitalize()
    fun generateAllBridgeRuleFiles() {
        generateSuperClass()
        fileModel.parserRules.forEach { generateEmfBridgeRuleFile(it) }
    }

    fun generateSuperClass() {
        val file = createFile("${capitalizedExtention}BridgeRule.kt", myGenDir + "/emf")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.emf;
            
            |import arithmetics.ArithmeticsFactory
            |import arithmetics.ArithmeticsPackage
            |import com.intellij.xtextLanguage.xtext.emf.EmfBridgeRule
            
            |abstract class ${capitalizedExtention}EmfBridgeRule : EmfBridgeRule {
            |protected val eFACTORY = ArithmeticsFactory.eINSTANCE
            |protected val ePACKAGE = ArithmeticsPackage.eINSTANCE
            |}
        """.trimMargin("|"))
        out.close()
    }

    fun generateEmfBridgeRuleFile(rule: ParserRule) {
        val file = createFile("${capitalizedExtention} ${rule.name}BridgeRule.kt", myGenDir + "/emf")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.emf
            
            |import com.intellij.${extention}Language.${extention}.${capitalizedExtention}ParserDefinition
            |import com.intellij.${extention}Language.${extention}.psi.*
            |import com.intellij.psi.PsiElement
            |import com.intellij.xtextLanguage.xtext.emf.*
            |import org.eclipse.emf.ecore.EObject
            
            |class ${capitalizedExtention}${rule.name}BridgeRule : ${capitalizedExtention}BridgeRule {
        """.trimMargin("|"))
        generateLiteralAssignmentMethod(rule, out)
        generateObjectAssignmentMethod(rule, out)
        generateRewriteMethod(rule, out)
        generateFactoryMethod(rule, out)
        out.print("\n}")
        out.close()
    }

    fun generateLiteralAssignmentMethod(rule: ParserRule, out: PrintWriter) {

    }

    fun generateObjectAssignmentMethod(rule: ParserRule, out: PrintWriter) {

    }

    fun generateRewriteMethod(rule: ParserRule, out: PrintWriter) {

    }

    fun generateFactoryMethod(rule: ParserRule, out: PrintWriter) {

    }


}