package com.intellij.xtext;

import com.intellij.testFramework.TestDataPath;

@TestDataPath("$CONTENT_ROOT/testData/completion/keys")
public class XtextCompletionTest extends XtextCompletionTestBase {
    public XtextCompletionTest() {
        super("/completion/keys");
    }



    public void testAllAfterGrammarIdEnd() {
        checkHasCompletions("enum", "terminal", "hidden (", "fragment", "generate", "import");
        checkDoesnotContain("ID", "returns");
    }

    public void testAfterRuledeclarationMiddle() {
        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testAllAfterGrammarIdMiddle() {
        checkHasCompletions("enum", "terminal", "hidden (", "fragment", "generate", "import");
        checkDoesnotContain("ID", "returns");
    }

    public void testAfterRuleIdEnd() {

        checkHasCompletions("returns", "hidden (");
        checkDoesnotContain("generate", "import", "ID", "enum", "terminal", "fragment");

    }

    public void testAfterRuleIdMiddle() {
        checkHasCompletions("returns", "hidden (");
        checkDoesnotContain("generate", "import", "ID", "enum", "terminal", "fragment");

    }

    public void testAfterRuleDeclarationEnd() {
        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testhiddenKeyword() {
        checkHasCompletions("hidden (");
    }

    public void testNoKeywordsAfterGrammarKeyword() {
        checkDoesnotContain("with", "hidden (", "enum", "terminal", "fragment", "generate", "import", "ID");
    }

    public void testNoKeywordsAfterGrammarKeywordMiddle() {
        checkDoesnotContain("with", "hidden (", "enum", "terminal", "fragment", "generate", "import", "ID");
    }

    public void testExcessiveInnerKeywords() {
        checkDoesnotContain("enum", "generate", "ID");
    }

    public void testAfterMultilineCommentEnd() {
        checkHasCompletions("enum", "terminal", "hidden (", "fragment", "generate", "import");

    }

    public void testAfterSinglelineCommentEnd() {
        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testAfterMultilineCommentMiddle() {
        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");


    }

    public void testAfterWhitespacesEnd1() {
        checkHasCompletions("enum", "terminal", "hidden (", "fragment", "generate", "import");
        checkDoesnotContain("ID", "returns");

    }

    public void testAfterWhitespacesEnd2() {

        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testAfterWhitespacesMiddle1() {
        checkHasCompletions("enum", "terminal", "hidden (", "fragment", "generate", "import");
        checkDoesnotContain("ID");
    }

    public void testAfterWhitespacesMiddle2() {
        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testErrorInGrammarId() {
        checkEmptyResolve();
    }

    public void testErrorInGrammarId2() {
        checkEmptyResolve();
    }

    public void testErrorInGrammarKeyword() {
        checkEmptyResolve();
    }

    public void testErrorInGrammarWithPart() {
        checkEmptyResolve();
    }

    public void testErrorInAbstractRuleId() {
        checkEmptyResolve();
    }

    public void testErrorInAbstractRuleColonMiss() {
        checkEmptyResolve();
    }

    public void testErrorInAbstractRuleAlternatives() {
        checkEmptyResolve();
    }

    public void testErrorRecoveryInAbstractRuleId() {

        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testErrorRecoveryInAbstractRuleColonMiss() {

        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testErrorRecoveryInAbstractRuleAlternatives() {

        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testErrorInEnumRuleId() {
        checkEmptyResolve();
    }

    public void testErrorRecoveryInEnumRuleId() {

        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testErrorInTerminalRuleId() {
        checkEmptyResolve();
    }

    public void testErrorRecoveryInTerminalRuleId() {

        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

    public void testErrorRecoveryAfterTwoErrorRules() {

        checkHasCompletions("enum", "terminal", "fragment");
        checkDoesnotContain("generate", "import", "returns", "hidden (", "ID");

    }

}
