package com.intellij.xtext;

import com.intellij.psi.PsiFile;
import com.intellij.testFramework.TestDataPath;
import com.intellij.xtextLanguage.xtext.generator.BnfGenerator;
import com.intellij.xtextLanguage.xtext.generator.BnfGeneratorOld;
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
        BnfGeneratorOld generator = new BnfGeneratorOld("newGrammar", model);
        assertEquals("^?([a-z]|[A-Z]|$|_)([a-z]|[A-Z]|$|_|[0-9])*", generator.getRegexpAsString(model.getTerminalRuleByName("ID").getMyRule()));
        assertEquals(".", generator.getRegexpAsString(model.getTerminalRuleByName("ANY_OTHER").getMyRule()));
        assertEquals("( |\\t|\\r|\\n)ref_STRING.", generator.getRegexpAsString(model.getTerminalRuleByName("WITH_REF").getMyRule()));
    }

    public void testParserRules() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;

        XtextFileModel model = new XtextFileModel(xtextFile);
        BnfGeneratorOld generator = new BnfGeneratorOld("newGrammar", model);

        assertEquals("'term' | C | REFERENCE_RUL ", generator.getRuleAlternativesAsString(model.getParserRuleByName("B").getMyRule()));
    }

    public void testParserRules2() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;

        XtextFileModel model = new XtextFileModel(xtextFile);
        BnfGeneratorOld generator = new BnfGeneratorOld("newGrammar", model);

        assertEquals("XImportSection ? AbstractElement * ", generator.getRuleAlternativesAsString(model.getParserRuleByName("Domainmodel").getMyRule()));

        assertEquals("PackageDeclaration | Entity ", generator.getRuleAlternativesAsString(model.getParserRuleByName("AbstractElement").getMyRule()));

        assertEquals("'entity' ValidID ('extends' JvmTypeReference ) ? '{' Feature * '}' ", generator.getRuleAlternativesAsString(model.getParserRuleByName("Entity").getMyRule()));

        assertEquals("ValidID ':' JvmTypeReference ", generator.getRuleAlternativesAsString(model.getParserRuleByName("Property").getMyRule()));

        assertEquals("'op' ValidID '(' (FullJvmFormalParameter (',' FullJvmFormalParameter ) * ) ? ')' ':' JvmTypeReference XBlockExpression ", generator.getRuleAlternativesAsString(model.getParserRuleByName("Operation").getMyRule()));

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
        assertEquals("JvmTypeReference", model.getParserRuleByName("JvmArgumentTypeReference").getReturnType());
        assertEquals(null, model.getParserRuleByName("JvmWildcardTypeReference").getReturnType());
        assertEquals("JvmLowerBound", model.getParserRuleByName("JvmLowerBoundAnded").getReturnType());
        assertTrue(model.getParserRuleByName("A").isFragment());
        assertTrue(model.getTerminalRuleByName("B").isFragment());
        assertFalse(model.getParserRuleByName("JvmWildcardTypeReference").isFragment());

    }

}
