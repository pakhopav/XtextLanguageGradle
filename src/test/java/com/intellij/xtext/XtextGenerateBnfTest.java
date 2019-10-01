package com.intellij.xtext;

import com.intellij.psi.PsiFile;
import com.intellij.testFramework.TestDataPath;
import com.intellij.xtextLanguage.xtext.generator.BnfGenerator;
import com.intellij.xtextLanguage.xtext.generator.BnfGeneratorOld;
import com.intellij.xtextLanguage.xtext.generator.ReferenceElement;
import com.intellij.xtextLanguage.xtext.generator.XtextFileModel;
import com.intellij.xtextLanguage.xtext.psi.XtextElementFactory;
import com.intellij.xtextLanguage.xtext.psi.XtextFile;
import com.intellij.xtextLanguage.xtext.psi.XtextParserRule;

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
        assertEquals("( |\\t|\\r|\\n)+ref_STRING.", generator.getRegexpAsString(model.getTerminalRuleByName("WITH_REF").getMyRule()));
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
        XtextParserRule r = XtextElementFactory.createParserRule("A : B ;");
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        assertEquals("Operation", model.getParserRuleByName("RuleFromFeature_OPERATION").getReturnType());


    }

    public void testGeneration2() {
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

    public void testNegatedTerminalRules() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;
        XtextFileModel model = new XtextFileModel(xtextFile);
        BnfGenerator generator = new BnfGenerator("newGrammar", model);
//
        assertEquals("[^l]", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG1").getMyRule()));
        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG2").getMyRule()));
        assertEquals("[^l-d]", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG3").getMyRule()));
        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG4").getMyRule()));
        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG5").getMyRule()));
        assertEquals("[^a\\na-z]", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG6").getMyRule()));
        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG7").getMyRule()));
        assertEquals("[^l]string", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG8").getMyRule()));
        assertEquals("[^l]( |\\t|\\r|\\n)+[^l-d]", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG9").getMyRule()));
        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG10").getMyRule()));

    }

    public void testGenerationImportedGrammar() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;
        XtextFileModel model = new XtextFileModel(xtextFile);
        BuildModelWithImports(model, model);
        BnfGenerator generator = new BnfGenerator("newGrammar", model);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(model.getParserRuleByName("Domainmodel"));
        assertNotNull(model.getParserRuleByName("Entity"));
        assertNotNull(model.getParserRuleByName("Feature"));
        assertNotNull(model.getParserRuleByName("Property"));

        assertNotNull(model.getParserRuleByName("JvmTypeReference"));
        assertNotNull(model.getParserRuleByName("ArrayBrackets"));
        assertNotNull(model.getParserRuleByName("XFunctionTypeRef"));
        assertNotNull(model.getParserRuleByName("JvmParameterizedTypeReference"));
        assertNotNull(model.getParserRuleByName("JvmArgumentTypeReference"));
        assertNotNull(model.getParserRuleByName("JvmWildcardTypeReference"));
        assertNotNull(model.getParserRuleByName("JvmUpperBound"));
        assertNotNull(model.getParserRuleByName("JvmUpperBoundAnded"));
        assertNotNull(model.getParserRuleByName("JvmLowerBound"));
        assertNotNull(model.getParserRuleByName("JvmLowerBoundAnded"));
        assertNotNull(model.getParserRuleByName("JvmTypeParameter"));
        assertNotNull(model.getParserRuleByName("QualifiedName"));
        assertNotNull(model.getParserRuleByName("QualifiedNameWithWildcard"));
        assertNotNull(model.getParserRuleByName("ValidID"));
        assertNotNull(model.getParserRuleByName("XImportSection"));
        assertNotNull(model.getParserRuleByName("XImportDeclaration"));
        assertNotNull(model.getParserRuleByName("QualifiedNameInStaticImport"));


    }

    public void testGenerationImportedGrammar2() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;
        XtextFileModel model = new XtextFileModel(xtextFile);
        BuildModelWithImports(model, model);
        BnfGenerator generator = new BnfGenerator("newGrammar", model);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotNull(model.getParserRuleByName("Domainmodel"));
        assertNotNull(model.getParserRuleByName("Entity"));
        assertNotNull(model.getParserRuleByName("Feature"));
        assertNotNull(model.getParserRuleByName("Property"));
        assertNotNull(model.getParserRuleByName("AbstractElement"));
        assertNotNull(model.getParserRuleByName("PackageDeclaration"));
        assertNotNull(model.getParserRuleByName("Operation"));
        assertNotNull(model.getParserRuleByName("JvmParameterizedTypeReference"));
        assertNotNull(model.getParserRuleByName("JvmArgumentTypeReference"));
        assertNotNull(model.getParserRuleByName("JvmWildcardTypeReference"));
        assertNotNull(model.getParserRuleByName("JvmUpperBound"));
        assertNotNull(model.getParserRuleByName("JvmUpperBoundAnded"));
        assertNotNull(model.getParserRuleByName("JvmLowerBound"));
        assertNotNull(model.getParserRuleByName("JvmLowerBoundAnded"));
        assertNotNull(model.getParserRuleByName("JvmTypeParameter"));
        assertNotNull(model.getParserRuleByName("QualifiedName"));
        assertNotNull(model.getParserRuleByName("QualifiedNameWithWildcard"));
        assertNotNull(model.getParserRuleByName("ValidID"));
        assertNotNull(model.getParserRuleByName("XImportSection"));
        assertNotNull(model.getParserRuleByName("XImportDeclaration"));
        assertNotNull(model.getParserRuleByName("QualifiedNameInStaticImport"));

        assertNotNull(model.getParserRuleByName("XExpression"));
        assertNotNull(model.getParserRuleByName("XAssignment"));
        assertNotNull(model.getParserRuleByName("OpSingleAssign"));
        assertNotNull(model.getParserRuleByName("OpMultiAssign"));
        assertNotNull(model.getParserRuleByName("XOrExpression"));
        assertNotNull(model.getParserRuleByName("OpOr"));
        assertNotNull(model.getParserRuleByName("XAndExpression"));
        assertNotNull(model.getParserRuleByName("OpAnd"));
        assertNotNull(model.getParserRuleByName("XEqualityExpression"));
        assertNotNull(model.getParserRuleByName("OpEquality"));
        assertNotNull(model.getParserRuleByName("XRelationalExpression"));
        assertNotNull(model.getParserRuleByName("OpCompare"));
        assertNotNull(model.getParserRuleByName("XOtherOperatorExpression"));
        assertNotNull(model.getParserRuleByName("OpOther"));

    }

    public void testXtext() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;
        XtextFileModel model = new XtextFileModel(xtextFile);
        BuildModelWithImports(model, model);
        for (ReferenceElement s : model.getMyReferences()) {
            if (s.getTargets() != null) {
                System.out.println(s.getTargets().toString());

            }
        }
        BnfGenerator generator = new BnfGenerator("newXtext", model);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void testEntityExampleReferencesImplementation() {
        PsiFile file = getXtextFile();
        XtextFile xtextFile = (XtextFile) file;
        XtextFileModel model = new XtextFileModel(xtextFile);
        BuildModelWithImports(model, model);
        BnfGenerator generator = new BnfGenerator("Entity", model);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
