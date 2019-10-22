package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class PsiImplUtilFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun geneneratePsiImplUtilFile() {
        val file = createFile(extention + "PsiImplUtil.java", myGenDir + "/psi/impl")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi.impl;
                        
            |import com.intellij.psi.*;
            |import $packageDir.psi.*;
            |import com.intellij.psi.util.PsiTreeUtil;
            
            |import java.util.Optional;
            
            |public class ${extention}PsiImplUtil {
            |    static ${extention}NameVisitor nameVisitor = new ${extention}NameVisitor();

        """.trimMargin("|"))
        fileModel.parserRules.stream()
                .filter { it.isReferenced == true }
                .forEach {
                    out.print("""
                       
                        |public static PsiElement setName(${extention}${it.name} element, String newName) {
                        |    //TODO
                        |    return element;
                        |}
                        
                        |public static String getName(${extention}${it.name} element) {
                        |    return Optional.ofNullable(getNameIdentifier(element))
                        |        .map(PsiElement::getText)
                        |        .orElse(null);
                        |}
                        |    
                        |public static PsiElement getNameIdentifier(${extention}${it.name} element) {
                        |    return nameVisitor.visit${it.name}(element);
                        |}
                        
                    """.trimMargin("|"))

                }


        out.print("}")
        out.close()

    }
}