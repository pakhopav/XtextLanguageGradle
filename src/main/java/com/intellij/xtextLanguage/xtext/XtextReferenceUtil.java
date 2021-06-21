package com.intellij.xtextLanguage.xtext;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.PsiNameIdentifierOwner;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.xtextLanguage.xtext.psi.XtextAbstractRule;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;
import com.intellij.xtextLanguage.xtext.psi.XtextGrammarID;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEGrammarGrammarID;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class XtextReferenceUtil {

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
                String[] nameParts = grammarName.split("\\.");
                int namePartsCount = nameParts.length;
                if (namePartsCount > 1) {
                    grammarName = nameParts[namePartsCount - 1];
                }
                if (grammarName.equals(name)) xtextFiles.add((XtextFile) psiFile);
            }
            return true;
        });
        return xtextFiles;
    }

    public static <T extends PsiNameIdentifierOwner> ArrayList<T> findRulesByNameInUsedGrammars(XtextFile file, String id) {
        ArrayList<T> result = new ArrayList<>();
        if (file == null) return result;
        List<String> usedGrammarsNames = PsiTreeUtil.findChildrenOfType(file, XtextREFERENCEGrammarGrammarID.class).stream().map(it -> it.getGrammarID().getText()).collect(Collectors.toList());
        usedGrammarsNames.forEach(grammarName -> {
            List<XtextFile> grammars = findGrammarsInProjectByName(file.getProject(), grammarName);
            if (grammars.size() == 1) {
                _findRulesByNameInUsedGrammars(grammars.get(0), id, result);
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
            List<XtextFile> grammars = findGrammarsInProjectByName(file.getProject(), grammarName);
            if (grammars.size() == 1) {
                _findRulesByNameInUsedGrammars(grammars.get(0), id, result);
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
}
