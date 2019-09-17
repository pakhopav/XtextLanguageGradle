package com.intellij.xtext;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiErrorElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiRecursiveElementVisitor;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
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
