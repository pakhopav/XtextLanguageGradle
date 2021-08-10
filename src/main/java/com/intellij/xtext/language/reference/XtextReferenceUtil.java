package com.intellij.xtext.language.reference;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.search.FilenameIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtext.language.psi.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.stream.Collectors;

public class XtextReferenceUtil {
    public static final List<String> DEFAULT_GRAMMARS = Arrays.asList("org.eclipse.xtext.common.Terminals", "org.eclipse.xtext.xbase.Xbase", "org.eclipse.xtext.Xtext", "org.eclipse.xtext.xbase.Xtype");

    public static List<XtextFile> findAllGrammarsInProject(Project project) {
        List<XtextFile> xtextFiles = new ArrayList<>();
        PsiManager psiManager = PsiManager.getInstance(project);
        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(fileOrDir -> {
            PsiFile psiFile = psiManager.findFile(fileOrDir);
            if (psiFile instanceof XtextFile) {
                xtextFiles.add((XtextFile) psiFile);
            }
            return true;
        });
        return xtextFiles;
    }

    public static List<XtextFile> findGrammarsInProjectByName(Project project, String name) {
        List<XtextFile> xtextFiles = new ArrayList<>();
        PsiManager psiManager = PsiManager.getInstance(project);
        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(fileOrDir -> {
            PsiFile psiFile = psiManager.findFile(fileOrDir);
            if (psiFile instanceof XtextFile) {
                String grammarName = PsiTreeUtil.findChildOfType(psiFile, XtextGrammarID.class).getText();
                if (grammarName.equals(name)) xtextFiles.add((XtextFile) psiFile);
            }
            return true;
        });
        return xtextFiles;
    }


    //TODO case while 2 grammars in project have the same name
    public static @Nullable
    XtextFile getDefaultGrammar(String grammarName, Project project) {
        String[] fqnParts = grammarName.split("\\.");
        String fileName = fqnParts[fqnParts.length - 1] + ".xtext";
        PsiFile[] found = FilenameIndex.getFilesByName(project, fileName, GlobalSearchScope.allScope(project));
        if (found.length == 1) {
            return (XtextFile) found[0];
        }
        return null;
    }


    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findRulesByNameInUsedGrammars(XtextFile file, String id) {
        ArrayList<T> result = new ArrayList<>();
        if (file == null) return result;
        List<String> usedGrammarsNames = PsiTreeUtil.findChildrenOfType(file, XtextREFERENCEGrammarGrammarID.class).stream().map(it -> it.getText()).collect(Collectors.toList());
        usedGrammarsNames.forEach(grammarName -> {
            if (DEFAULT_GRAMMARS.contains(grammarName)) {
                _findRulesByNameInUsedGrammars(getDefaultGrammar(grammarName, file.getProject()), id, result);
            } else {
                List<XtextFile> grammars = findGrammarsInProjectByName(file.getProject(), grammarName);
                if (grammars.size() == 1) {
                    _findRulesByNameInUsedGrammars(grammars.get(0), id, result);
                }
            }
        });
        return result;
    }

    private static <T extends PsiNameIdentifierOwner> ArrayList<T> _findRulesByNameInUsedGrammars(XtextFile file, String id, ArrayList<T> result) {
        if (file == null) return result;
        List<T> allRules = new ArrayList(PsiTreeUtil.findChildrenOfType(file, XtextAbstractRule.class));
        result.addAll(allRules.stream().filter(r -> r.getName().equals(id)).collect(Collectors.toList()));
        List<String> usedGrammarsNames = PsiTreeUtil.findChildrenOfType(file, XtextREFERENCEGrammarGrammarID.class).stream().map(it -> it.getGrammarID().getText()).collect(Collectors.toList());
        usedGrammarsNames.forEach(grammarName -> {
            if (DEFAULT_GRAMMARS.contains(grammarName)) {
                _findRulesByNameInUsedGrammars(getDefaultGrammar(grammarName, file.getProject()), id, result);
            } else {
                List<XtextFile> grammars = findGrammarsInProjectByName(file.getProject(), grammarName);
                if (grammars.size() == 1) {
                    _findRulesByNameInUsedGrammars(grammars.get(0), id, result);
                }
            }
        });
        return result;
    }


    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findElementsInCurrentFile(PsiFile file, Class<T> tClass, String id) {
        ArrayList<T> result = new ArrayList<>();
        XtextFile xtextFile = (XtextFile) file;
        if (xtextFile != null) {
            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(xtextFile, tClass));
            for (T property : elements) {
                if (id.equals(property.getName())) {
                    result.add(property);
                }
            }
        }
        return result;
    }

    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findRulesInCurrentFile(PsiFile file, String id) {
        ArrayList<T> result = new ArrayList<>();
        XtextFile xtextFile = (XtextFile) file;
        if (xtextFile != null) {
            List<T> allRules = new ArrayList(PsiTreeUtil.findChildrenOfType(xtextFile, XtextAbstractRule.class));
            for (T property : allRules) {
                if (id.equals(property.getName())) {
                    result.add(property);
                }
            }
        }

        return result;
    }

    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findAllRulesInCurrentFile(PsiFile file) {
        ArrayList<T> result = new ArrayList<>();
        XtextFile xtextFile = (XtextFile) file;
        if (xtextFile != null) {
            List<T> elements = new ArrayList(PsiTreeUtil.findChildrenOfType(xtextFile, XtextAbstractRule.class));
            result.addAll(elements);
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


    public static List<String> findImportedModelsNames(XtextFile xtextFile) {
        List<String> result = new ArrayList<>();
        _findImportedModelsNames(xtextFile, result);
        return result;
    }

    private static void _findImportedModelsNames(XtextFile xtextFile, List<String> result) {
        List<String> modelNames = PsiTreeUtil.findChildrenOfType(xtextFile, XtextReferencedMetamodel.class).stream().map(it -> it.getREFERENCEEPackageSTRING().getText()).collect(Collectors.toList());
        result.addAll(modelNames);
        List<String> usedGrammarsNames = PsiTreeUtil.findChildrenOfType(xtextFile, XtextREFERENCEGrammarGrammarID.class).stream().map(it -> it.getGrammarID().getText()).collect(Collectors.toList());
        usedGrammarsNames.forEach(grammarName -> {
            List<XtextFile> grammars = findGrammarsInProjectByName(xtextFile.getProject(), grammarName);
            if (grammars.size() == 1) {
                _findImportedModelsNames(grammars.get(0), result);
            }
        });
    }

    public static boolean isClassifierPresentInJar(JarFile jar, String key) {
        VirtualFile ecoreFile = null;
        JarEntry modelEntry = jar.getJarEntry("model");
        if (modelEntry != null) {

        }
        jar.entries();
        return false;
    }
}
