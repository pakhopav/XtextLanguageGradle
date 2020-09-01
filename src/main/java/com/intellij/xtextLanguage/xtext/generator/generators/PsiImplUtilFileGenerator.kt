package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class PsiImplUtilFileGenerator(extension: String, val fileModel: XtextMainModel) : AbstractGenerator(extension) {
    fun geneneratePsiImplUtilFile() {
        val file = createFile(extension.capitalize() + "PsiImplUtil.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi.impl;
                        
            |import com.intellij.psi.*;
            |import $packageDir.psi.*;
            |import com.intellij.psi.util.PsiTreeUtil;
            
            |import java.util.Optional;
            
            |public class ${extension.capitalize()}PsiImplUtil {
            |    static ${extension.capitalize()}NameVisitor nameVisitor = new ${extension.capitalize()}NameVisitor();

        """.trimMargin("|"))
        fileModel.parserRules.stream()
                .filter { it.isReferenced == true }
                .forEach {
                    out.print("""
                       
                        |public static PsiElement setName(${extension.capitalize()}${it.name} element, String newName) {
                        |    //TODO
                        |    return element;
                        |}
                        
                        |public static String getName(${extension.capitalize()}${it.name} element) {
                        |    return Optional.ofNullable(getNameIdentifier(element))
                        |        .map(PsiElement::getText)
                        |        .orElse(null);
                        |}
                        |    
                        |public static PsiElement getNameIdentifier(${extension.capitalize()}${it.name} element) {
                        |    return nameVisitor.visit${it.name}(element);
                        |}
                        
                    """.trimMargin("|"))

                }


        out.print("}")
        out.close()

    }
}