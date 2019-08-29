package com.intellij.xtext;

import com.intellij.testFramework.TestDataPath;

@TestDataPath("$CONTENT_ROOT/testData/completion/keys")
public class XtextCompletionTest extends XtextCompletionTestBase {
    public XtextCompletionTest() {
        super("/completion/keys");
    }



    public void testAllAfterGrammarIdEnd() {
        checkHasCompletions("with", "hidden (", "enum", "terminal", "fragment", "generate", "import");
        checkDoesnotContain("returns");
    }

    public void testAfterRuledeclarationMiddle() {
        checkHasCompletions("enum", "terminal", "fragment");
    }

    public void testAllAfterGrammarIdMiddle() {
        checkHasCompletions("with", "hidden (", "enum", "terminal", "fragment", "generate", "import");
        checkDoesnotContain("returns");
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

    public void testNoKeywordsAfterGrammarKeyword() {
        checkDoesnotContain("with", "hidden (", "enum", "terminal", "fragment", "generate", "import");
    }

    public void testNoKeywordsAfterGrammarKeywordMiddle() {
        checkDoesnotContain("with", "hidden (", "enum", "terminal", "fragment", "generate", "import");
    }

    public void testExcessiveInnerKeywords() {
        checkDoesnotContain("enum", "generate");
    }

    public void testAfterMultilineCommentEnd() {
        checkHasCompletions("enum", "terminal", "hidden (", "fragment", "generate", "import");
    }

    public void testAfterSinglelineCommentEnd() {
        checkHasCompletions("enum", "terminal", "fragment");
    }

    public void testAfterMultilineCommentMiddle() {
        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (");


    }

    public void testAferWhitespacesEnd1() {
        checkHasCompletions("enum", "terminal", "hidden (", "fragment", "generate", "import");
    }

    public void testAferWhitespacesEnd2() {
        checkHasCompletions("enum", "terminal", "fragment");
    }

    public void testAferWhitespacesMiddle1() {
        checkHasCompletions("enum", "terminal", "fragment");
    }

    public void testAferWhitespacesMiddle2() {
        checkHasCompletions("enum", "terminal", "fragment");
    }
}
