package com.intellij.xtext;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

abstract class XtextCompletionTestBase extends LightPlatformCodeInsightFixtureTestCase {

    private final String myDataFolder;

    XtextCompletionTestBase(@NotNull String dataFolder) {
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

    protected List<String> getCompletionVariants() {
        String input = getCurrentInputFileName();
        List<String> result = myFixture.getCompletionVariants(input);
        assertNotEmpty(result);
        return result;
    }

    protected void checkHasCompletions(String... completions) {
        List<String> variants = getCompletionVariants();
        assertContainsElements(variants, completions);
    }

    protected void checkDoesnotContain(String... completions) {
        List<String> variants = getCompletionVariants();

        assertDoesntContain(variants, completions);
    }

    protected void checkEmptyResolve() {
        String input = getCurrentInputFileName();
        List<String> variants = myFixture.getCompletionVariants(input);
        assertEmpty(variants);
    }
}
