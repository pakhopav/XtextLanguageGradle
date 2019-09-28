package com.intellij.xtextLanguage.xtext;

public class XtextUtil {
//    public static <T extends XtextNamedElement> List<T> findElementsInProject(Project project, Class<T> tClass, String Id) {
//        List<T> result = null;
//
//        Collection<VirtualFile> virtualFiles =
//                FileTypeIndex.getFiles(XtextFileType.INSTANCE, GlobalSearchScope.allScope(project));
//        for (VirtualFile virtualFile : virtualFiles) {
//            XtextFileImpl simpleFile = (XtextFileImpl) PsiManager.getInstance(project).findFile(virtualFile);
//            if (simpleFile != null) {
//                T[] elements = PsiTreeUtil.getChildrenOfType(simpleFile, tClass);
//                if (elements != null) {
//                    for (T property : elements) {
//                        if (Id.equals(property.getName())) {
//                            if (result == null) {
//                                result = new ArrayList<T>();
//                            }
//                            result.add(property);
//                        }
//                    }
//                }
//            }
//        }
//        return result != null ? result : Collections.<T>emptyList();
//    }
//
//    public static <T extends XtextNamedElement> List<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String Id) {
//        List<T> result = null;
//        XtextFileImpl xtextFileImpl = (XtextFileImpl) file;
//        if (xtextFileImpl != null) {
//            T[] elements = PsiTreeUtil.getChildrenOfType(xtextFileImpl, tClass);
//            if (elements != null) {
//                for (T property : elements) {
//                    if (Id.equals(property.getName())) {
//                        if (result == null) {
//                            result = new ArrayList<T>();
//                        }
//                        result.add(property);
//                    }
//                }
//            }
//        }
//
//        return result != null ? result : Collections.<T>emptyList();
//    }
//
//    public static <T extends PsiElement> List<T> findElementsInProject(Project project, Class<T> tClass) {
//        List<T> result = new ArrayList<>();
//        Collection<VirtualFile> virtualFiles =
//                FileTypeIndex.getFiles(XtextFileType.INSTANCE, GlobalSearchScope.allScope(project));
//        for (VirtualFile virtualFile : virtualFiles) {
//            XtextFileImpl simpleFile = (XtextFileImpl) PsiManager.getInstance(project).findFile(virtualFile);
//            if (simpleFile != null) {
//                T[] elements = PsiTreeUtil.getChildrenOfType(simpleFile, tClass);
//                if (elements != null) {
//                    Collections.addAll(result, elements);
//                }
//            }
//        }
//        return result;
//    }
//
//    public static <T extends PsiElement> List<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass) {
//        List<T> result = new ArrayList<>();
//        XtextFileImpl xtextFileImpl = (XtextFileImpl) file;
//        if (xtextFileImpl != null) {
//            T[] elements = PsiTreeUtil.getChildrenOfType(xtextFileImpl, tClass);
//            if (elements != null) {
//                Collections.addAll(result, elements);
//            }
//        }
//
//        return result;
//    }
}
