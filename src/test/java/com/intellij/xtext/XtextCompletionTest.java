package com.intellij.xtext;

import com.intellij.testFramework.TestDataPath;

import java.util.List;

@TestDataPath("$CONTENT_ROOT/testData/completion/keys")
public class XtextCompletionTest extends XtextCompletionTestBase {
    public XtextCompletionTest() {
        super("/completion/keys");
    }



    public void testAllAfterGrammarIdEnd() {
        checkHasCompletions("with", "hidden (", "enum", "terminal", "fragment", "generate", "import");
    }

    public void testAllRulesBetweenRules() {
        checkHasCompletions("enum", "terminal", "fragment");
    }

    public void testAllAfterGrammarIdMiddle() {
        checkHasCompletions("with", "hidden (", "enum", "terminal", "fragment", "generate", "import");
    }

    public void testAfterRuleIdEnd() {
        checkHasCompletions("returns", "hidden (");
    }

    public void testAfterRuleIdMiddle() {
        checkHasCompletions("returns", "hidden (");
    }

    public void testAllRulesAfterRulesEnd() {
        checkHasCompletions("enum", "terminal", "fragment");
    }

    public void testhiddenKeyword() {
        checkHasCompletions("hidden (");
    }

    public void testNoKeywordsFromErrorState() {
        List<String> variants = getCompletionVariants();

        assertDoesntContain(variants, "enum");
    }

    public void testExcessiveInnerKeywords() {
        List<String> variants = getCompletionVariants();

        assertDoesntContain(variants, "enum", "generete");
    }

    public void testAfterMultilineComment() {
        checkHasCompletions("enum", "terminal", "hidden (", "fragment", "generate", "import");
    }
}
