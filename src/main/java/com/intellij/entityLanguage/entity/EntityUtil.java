package com.intellij.entityLanguage.entity;

import com.intellij.entityLanguage.entity.psi.EntityFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.util.PsiTreeUtil;

import java.util.ArrayList;
import java.util.List;

public class EntityUtil {

    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
        ArrayList<T> result = new ArrayList<>();
        EntityFile entityFile = (EntityFile) file;
        if (entityFile != null) {

            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(entityFile, tClass));

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
        EntityFile entityFile = (EntityFile) file;
        if (entityFile != null) {
            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(entityFile, tClass));
            result.addAll(elements);

        }

        return result;
    }
}
