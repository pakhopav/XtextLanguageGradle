package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class UtilFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateUtilFile() {
        val file = createFile(extention.capitalize() + "Util.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            
            |import com.intellij.psi.PsiFile;
            |import com.intellij.psi.PsiNameIdentifierOwner;
            |import com.intellij.psi.util.PsiTreeUtil;
            |import $packageDir.psi.${extention.capitalize()}File;
            
            |import java.util.ArrayList;
            |import java.util.Collections;
            |import java.util.List;
            
            |public class ${extention.capitalize()}Util {
            |
            |    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
            |        ArrayList<T> result = new ArrayList<>();
            |        ${extention.capitalize()}File ${extention.decapitalize()}File = (${extention.capitalize()}File) file;
            |        if (${extention.decapitalize()}File != null) {
            |
            |           List<T> elements = new ArrayList (PsiTreeUtil.findChildrenOfType(${extention.decapitalize()}File, tClass));
            |
            |            for (T property : elements) {
            |                if (Id.equals(property.getName())) {
            |                    result.add(property);
            |                }
            |            }
            |
            |        }
            |
            |        return result ;
            |    }
            |
            |    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass) {
            |        ArrayList<T> result = new ArrayList<>();
            |        ${extention.capitalize()}File ${extention.decapitalize()}File = (${extention.capitalize()}File) file;
            |        if (${extention.decapitalize()}File != null) {
            |            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(${extention.decapitalize()}File, tClass));
            |                result.addAll(elements);
            |            
            |       }
            |
            |        return result;
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}