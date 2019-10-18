package com.intellij.xtext;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import com.intellij.xtextLanguage.xtext.generator.XtextFileModel;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;
import com.intellij.xtextLanguage.xtext.psi.XtextREFERENCEGrammarGrammarID;
import org.jetbrains.annotations.NotNull;

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

    protected String getCurrentInputFileName() {
        return getTestName(true) + ".xtext";
    }

    protected PsiFile getXtextFile() {
        String fileName = getCurrentInputFileName();
        PsiFile file = myFixture.configureByFile(fileName);


        return file;
    }

    protected PsiFile getFile(String fileName) {
        PsiFile file = myFixture.configureByFile(fileName + ".xtext");
        return file;
    }

    protected PsiFile getBnfFile(String fileName) {
        PsiFile file = myFixture.configureByFile(fileName + ".bnf");
        return file;
    }

    protected PsiFile getFileWithAbsolutePath(String path) {
        PsiFile file = myFixture.configureByFile(path);
        return file;
    }

    protected void BuildModelWithImports(XtextFileModel mainModel, XtextFileModel importedModel) {
        XtextREFERENCEGrammarGrammarID[] grammars = importedModel.getMyImportedGrammars();
        if (grammars != null) {
            for (XtextREFERENCEGrammarGrammarID name : grammars) {
                XtextFile file = (XtextFile) getFile(name.getText());
                if (file != null) {
                    XtextFileModel newModel = new XtextFileModel(file);
                    BuildModelWithImports(mainModel, newModel);
                }
            }
        }
        if (mainModel != importedModel) {
            mainModel.getMyEnumRules().addAll(importedModel.getMyEnumRules());
            mainModel.getMyParserRules().addAll(importedModel.getMyParserRules());
            mainModel.getMyTerminalRules().addAll(importedModel.getMyTerminalRules());
            mainModel.getMyReferences().addAll(importedModel.getMyReferences());
        }


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
