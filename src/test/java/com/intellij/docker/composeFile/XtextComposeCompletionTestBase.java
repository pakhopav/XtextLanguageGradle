package com.intellij.docker.composeFile;

import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

import java.util.List;

abstract class XtextComposeCompletionTestBase extends LightPlatformCodeInsightFixtureTestCase {

  private final String myDataFolder;

  XtextComposeCompletionTestBase(@NotNull String dataFolder) {
    myDataFolder = dataFolder;
  }

  @Override
  protected final String getTestDataPath() {
    return getBasePath();
  }

  @Override
  protected final String getBasePath() {
    return AllComposeTests.getTestDataRoot() + myDataFolder;
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
}
