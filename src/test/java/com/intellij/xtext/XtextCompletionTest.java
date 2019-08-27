package com.intellij.xtext;

import com.intellij.testFramework.TestDataPath;

@TestDataPath("$CONTENT_ROOT/testData//completion/keys")
public class XtextCompletionTest extends XtextCompletionTestBase {
    public XtextCompletionTest() {
        super("/completion/keys");
    }

    public void testKwWithAfterGrammarId() {
        checkHasCompletions("with");
    }

    public void testAllAfterGrammarIdEnd() {
        checkHasCompletions("with", "hidden", "enum", "terminal", "fragment", "generate", "import");
    }

    public void testAllRulesBetweenRules() {
        checkHasCompletions("enum", "terminal", "fragment");
    }

    public void testAllAfterGrammarIdMiddle() {
        checkHasCompletions("with", "hidden", "enum", "terminal", "fragment", "generate", "import");
    }

    public void testKwHiddenKwReturnsEnd() {
        checkHasCompletions("returns", "hidden");
    }

    public void testKwHiddenKwReturnsMiddle() {
        checkHasCompletions("returns", "hidden");
    }

    public void testAllRulesAfterRulesEnd() {
        checkHasCompletions("enum", "terminal", "fragment");
    }


}
