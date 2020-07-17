package com.intellij.calcLanguage.calc;

import com.intellij.calcLanguage.calc.psi.calcFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;
import java.util.List;

public class calcUtil {

    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
        ArrayList<T> result = new ArrayList<>();
        calcFile calcFile = (calcFile) file;
        if (calcFile != null) {

            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(calcFile, tClass));

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
        calcFile calcFile = (calcFile) file;
        if (calcFile != null) {
            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(calcFile, tClass));
            result.addAll(elements);

        }

        return result;
    }
}
