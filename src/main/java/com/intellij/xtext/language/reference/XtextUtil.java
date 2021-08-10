package com.intellij.xtext.language.reference;

import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtext.language.psi.XtextFile;

import java.util.ArrayList;
import java.util.List;

public class XtextUtil {

    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
        ArrayList<T> result = new ArrayList<>();
        XtextFile xtextFile = (XtextFile) file;
        if (xtextFile != null) {
            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(xtextFile, tClass));
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
        XtextFile xtextFile = (XtextFile) file;
        if (xtextFile != null) {
            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(xtextFile, tClass));
            result.addAll(elements);
        }
        return result;
    }
}
