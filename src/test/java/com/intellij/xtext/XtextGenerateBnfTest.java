package com.intellij.xtext;

import com.intellij.psi.PsiFile;
import com.intellij.testFramework.TestDataPath;
import com.intellij.xtextLanguage.xtext.generator.BnfGenerator;
import com.intellij.xtextLanguage.xtext.generator.XtextFileModel;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;

import java.io.IOException;

@TestDataPath("$CONTENT_ROOT/testData/generation/generateBnf")
public class XtextGenerateBnfTest extends XtextGenerateBnfTestBase {
    public XtextGenerateBnfTest() {
        super("/generation/generateBnf");
    }

    public void testFindXtextFile() {
        PsiFile file = getXtextFile();
        assertTrue(file != null);
    }

    public void testCheckErrorsCorrect() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;
        MyErrorFinder errorFinder = new MyErrorFinder();
        errorFinder.visitFile(file);

        assertFalse(errorFinder.wasError);
    }

    public void testComputingRules1() throws IOException {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;
        XtextFileModel model = new XtextFileModel(xtextFile);


        assertTrue(model.getMyParserRules().size() == 2);
    }

    public void testTerminalRules() throws IOException {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;

        XtextFileModel model = new XtextFileModel(xtextFile);

        assertEquals("^?([a-z]|[A-Z]|$|_)([a-z]|[A-Z]|$|_|[0-9])*", model.getTerminalRuleByName("ID").regexp);
        assertEquals(".", model.getTerminalRuleByName("ANY_OTHER").regexp);
        assertEquals("( |\\t|\\r|\\n)ref_STRING.", model.getTerminalRuleByName("WITH_REF").regexp);
    }

    public void testParserRules() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;

        XtextFileModel model = new XtextFileModel(xtextFile);

        assertEquals("'term' | C | REFERENCE_RUL ", model.getParserRuleByName("B").alternatives);
    }

    public void testParserRules2() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;

        XtextFileModel model = new XtextFileModel(xtextFile);

        assertEquals("XImportSection ? AbstractElement * ", model.getParserRuleByName("Domainmodel").alternatives);

        assertEquals("PackageDeclaration | Entity ", model.getParserRuleByName("AbstractElement").alternatives);

        assertEquals("'entity' ValidID ('extends' JvmTypeReference ) ? '{' Feature * '}' ", model.getParserRuleByName("Entity").alternatives);

        assertEquals("ValidID ':' JvmTypeReference ", model.getParserRuleByName("Property").alternatives);

        assertEquals("'op' ValidID '(' (FullJvmFormalParameter (',' FullJvmFormalParameter ) * ) ? ')' ':' JvmTypeReference XBlockExpression ", model.getParserRuleByName("Operation").alternatives);

    }

    public void testGeneration1() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;
        XtextFileModel model = new XtextFileModel(xtextFile);
        BnfGenerator generator = new BnfGenerator("newGrammar", model);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void testReturnsAndFragment() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;
        XtextFileModel model = new XtextFileModel(xtextFile);
        assertEquals("JvmTypeReference", model.getParserRuleByName("JvmArgumentTypeReference").returnType);
        assertEquals(null, model.getParserRuleByName("JvmWildcardTypeReference").returnType);
        assertEquals("JvmLowerBound", model.getParserRuleByName("JvmLowerBoundAnded").returnType);
        assertTrue(model.getParserRuleByName("A").isFragment);
        assertTrue(model.getTerminalRuleByName("B").isFragment);
        assertFalse(model.getParserRuleByName("JvmWildcardTypeReference").isFragment);

    }

}
