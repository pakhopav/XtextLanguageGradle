package com.intellij.smalljavaLanguage.smalljava;

import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.smalljavaLanguage.smalljava.psi.SmalljavaFile;

import java.util.ArrayList;
import java.util.List;

public class SmalljavaUtil {

    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
        ArrayList<T> result = new ArrayList<>();
        SmalljavaFile smalljavaFile = (SmalljavaFile) file;
        if (smalljavaFile != null) {

            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(smalljavaFile, tClass));

            for (T property : elements) {
                if (Id.equals(property.getName())) {
                    result.add(property);
                }
            }

        }

        return result;
    }

    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass) {
        ArrayList<T> result = new ArrayList<>();
        SmalljavaFile smalljavaFile = (SmalljavaFile) file;
        if (smalljavaFile != null) {
            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(smalljavaFile, tClass));
            result.addAll(elements);

        }

        return result;
    }
}
