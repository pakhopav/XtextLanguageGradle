package com.intellij.xtext;

//import com.intellij.entityLanguage.entity.psi.EntityFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEGrammarGrammarID;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class XtextGenerateBnfTestBase extends LightPlatformCodeInsightFixtureTestCase {
    private final String myDataFolder;

    XtextGenerateBnfTestBase(@NotNull String dataFolder) {
        myDataFolder = dataFolder;
    }

    @Override
    protected final String getTestDataPath() {
        return getBasePath();
    }

    @Override
    protected final String getBasePath() {
        return AllTests.getTestDataRoot() + myDataFolder;
    }

    private String getCurrentInputFileName() {
        return getTestName(true) + ".xtext";
    }

    XtextFile getXtextFile() {
        String fileName = getCurrentInputFileName();
        PsiFile file = myFixture.configureByFile(fileName);


        return (XtextFile) file;
    }

    private XtextFile getXtextFileByName(String fileName) {
        return (XtextFile) myFixture.configureByFile(fileName + ".xtext");
    }

    protected PsiFile getBnfFile(String fileName) {
        return myFixture.configureByFile(fileName + ".bnf");
    }

//    protected EntityFile getEntityFile(String fileName) {
//        return (EntityFile) myFixture.configureByFile(fileName + ".entity");
//    }

    protected PsiFile getFileWithAbsolutePath(String path) {
        PsiFile file = myFixture.configureByFile(path);
        return file;
    }

    List<XtextFile> findAllFiles() {
        ArrayList<XtextFile> listOfFiles = new ArrayList<>();
        XtextFile mainFile = getXtextFile();
        listOfFiles.add(mainFile);
        List<XtextREFERENCEGrammarGrammarID> grammars = new ArrayList<>(PsiTreeUtil.findChildrenOfType(mainFile, XtextREFERENCEGrammarGrammarID.class));
        listOfFiles.addAll(findAllFilesRecursively(grammars));
        return listOfFiles;
    }

    private List<XtextFile> findAllFilesRecursively(List<XtextREFERENCEGrammarGrammarID> grammars) {
        ArrayList<XtextFile> listOfFiles = new ArrayList<>();
        if (grammars != null) {

            for (XtextREFERENCEGrammarGrammarID name : grammars) {
                XtextFile file = getXtextFileByName(name.getText());
                listOfFiles.add(file);
                List<XtextREFERENCEGrammarGrammarID> listOfGrammars = new ArrayList<>(PsiTreeUtil.findChildrenOfType(file, XtextREFERENCEGrammarGrammarID.class));
                listOfFiles.addAll(findAllFilesRecursively(listOfGrammars));
            }
        }
        return listOfFiles;
    }


    protected static class MyErrorFinder extends PsiRecursiveElementVisitor {
        private static final MyErrorFinder INSTANCE = new MyErrorFinder();
        public boolean wasError = false;

        @Override
        public void visitElement(PsiElement element) {
            if (element instanceof PsiErrorElement) wasError = true;
            super.visitElement(element);
        }

    }


}
