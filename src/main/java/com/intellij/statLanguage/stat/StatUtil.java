package com.intellij.statLanguage.stat;

import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.statLanguage.stat.psi.StatFile;

import java.util.ArrayList;
import java.util.List;

public class StatUtil {

    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
        ArrayList<T> result = new ArrayList<>();
        StatFile statFile = (StatFile) file;
        if (statFile != null) {

            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(statFile, tClass));

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
        StatFile statFile = (StatFile) file;
        if (statFile != null) {
            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(statFile, tClass));
            result.addAll(elements);

        }

        return result;
    }
}
