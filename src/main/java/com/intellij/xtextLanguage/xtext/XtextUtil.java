package com.intellij.xtextLanguage.xtext;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;
import com.intellij.xtextLanguage.xtext.psi.XtextNamedElement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class XtextUtil {
    public static <T extends XtextNamedElement> List<T> findElementsInProject(Project project, Class<T> tClass, String Id) {
        List<T> result = null;

        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(XtextFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            XtextFile simpleFile = (XtextFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                T[] elements = PsiTreeUtil.getChildrenOfType(simpleFile, tClass);
                if (elements != null) {
                    for (T property : elements) {
                        if (Id.equals(property.getName())) {
                            if (result == null) {
                                result = new ArrayList<T>();
                            }
                            result.add(property);
                        }
                    }
                }
            }
        }
        return result != null ? result : Collections.<T>emptyList();
    }

    public static <T extends XtextNamedElement> List<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
        List<T> result = null;
        XtextFile xtextFile = (XtextFile) file;
        if (xtextFile != null) {
            T[] elements = PsiTreeUtil.getChildrenOfType(xtextFile, tClass);
            if (elements != null) {
                for (T property : elements) {
                    if (Id.equals(property.getName())) {
                        if (result == null) {
                            result = new ArrayList<T>();
                        }
                        result.add(property);
                    }
                }
            }
        }

        return result != null ? result : Collections.<T>emptyList();
    }

    public static <T extends PsiElement> List<T> findElementsInProject(Project project, Class<T> tClass) {
        List<T> result = new ArrayList<>();
        Collection<VirtualFile> virtualFiles =
                FileTypeIndex.getFiles(XtextFileType.INSTANCE, GlobalSearchScope.allScope(project));
        for (VirtualFile virtualFile : virtualFiles) {
            XtextFile simpleFile = (XtextFile) PsiManager.getInstance(project).findFile(virtualFile);
            if (simpleFile != null) {
                T[] elements = PsiTreeUtil.getChildrenOfType(simpleFile, tClass);
                if (elements != null) {
                    Collections.addAll(result, elements);
                }
            }
        }
        return result;
    }

    public static <T extends PsiElement> List<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass) {
        List<T> result = new ArrayList<>();
        XtextFile xtextFile = (XtextFile) file;
        if (xtextFile != null) {
            T[] elements = PsiTreeUtil.getChildrenOfType(xtextFile, tClass);
            if (elements != null) {
                Collections.addAll(result, elements);
            }
        }

        return result;
    }
}
