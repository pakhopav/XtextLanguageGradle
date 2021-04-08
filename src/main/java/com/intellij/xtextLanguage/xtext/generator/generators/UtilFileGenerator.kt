package com.intellij.xtextLanguage.xtext.generator.generators

import java.io.FileOutputStream
import java.io.PrintWriter

class UtilFileGenerator(extension: String, rootPath: String) : AbstractGenerator(extension, rootPath) {
    fun generateUtilFile() {
        val file = createFile(extension.capitalize() + "Util.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print(
            """
            |package $packageDir;
            
            |import com.intellij.psi.PsiFile;
            |import com.intellij.psi.PsiNameIdentifierOwner;
            |import com.intellij.psi.util.PsiTreeUtil;
            |import $packageDir.psi.${extension.capitalize()}File;
            
            |import java.util.ArrayList;
            |import java.util.Collections;
            |import java.util.List;
            
            |public class ${extension.capitalize()}Util {
            |
            |    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
            |        ArrayList<T> result = new ArrayList<>();
            |        ${extension.capitalize()}File ${extension.decapitalize()}File = (${extension.capitalize()}File) file;
            |        if (${extension.decapitalize()}File != null) {
            |            List<T> elements = new ArrayList (PsiTreeUtil.findChildrenOfType(${extension.decapitalize()}File, tClass));
            |            for (T property : elements) {
            |                if (Id.equals(property.getName())) {
            |                    result.add(property);
            |                }
            |            }
            |        }
            |        return result ;
            |    }
            |
            |    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass) {
            |        ArrayList<T> result = new ArrayList<>();
            |        ${extension.capitalize()}File ${extension.decapitalize()}File = (${extension.capitalize()}File) file;
            |        if (${extension.decapitalize()}File != null) {
            |            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(${extension.decapitalize()}File, tClass));
            |                result.addAll(elements);
            |        }
            |        return result;
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}