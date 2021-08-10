package com.intellij.xtext;

//import com.intellij.entityLanguage.entity.emf.EmfObjectFactory;

import com.intellij.psi.PsiFile;
import com.intellij.testFramework.TestDataPath;
import com.intellij.xtext.generator.generators.MainGenerator;
import com.intellij.xtext.language.psi.XtextFile;
import com.intellij.xtext.metamodel.model.MetaContext;
import com.intellij.xtext.metamodel.model.MetaContextImpl;

import java.io.File;
import java.io.IOException;
import java.util.List;

@TestDataPath("$CONTENT_ROOT/testData/generation/generateBnf")
public class XtextGenerateBnfTest extends XtextGenerateBnfTestBase {
    public XtextGenerateBnfTest() {
        super("/generation/generateBnf");
    }

    public void testFindXtextFile() {

        File f = new File("src/main/resources/grammars/Terminals.xtext");
        Boolean b = f.canRead();
        PsiFile file = getXtextFile();
        assertTrue(file != null);
    }


//    public void testXtext() {
//        List<XtextFile> allXtextFiles = findAllFiles();
//        XtextMainModel model = new XtextMainModel(allXtextFiles);
//        Generator generator = new Generator("newXtext", model);
//        try {
//            generator.generate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }

    //    public void testInvalidRules() {
//        List<XtextFile> allXtextFiles = findAllFiles();
//        XtextMainModel model = new XtextMainModel(allXtextFiles);
//        Generator generator = new Generator("ErrRules", model);
//        try {
//            generator.generate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
    public void testActionsGrammar() {
        List<XtextFile> allXtextFiles = findAllFiles();
        MetaContextImpl c = new MetaContextImpl(allXtextFiles);
//    XtextMainModel model = new XtextMainModel(allXtextFiles);
//    MainGenerator generator = new MainGenerator("simple", model);
//    try {
//        generator.generate();
//    } catch (IOException e) {
//        e.printStackTrace();
//    }
    }


    //    public void testSimple() {
//        List<XtextFile> allXtextFiles = findAllFiles();
//        XtextMainModel model = new XtextMainModel(allXtextFiles);
//        MainGenerator generator = new MainGenerator("simple", model);
//        try {
//            generator.generate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
    public void testXtextCopy() {
        List<XtextFile> allXtextFiles = findAllFiles();
        MetaContext context = new MetaContextImpl(allXtextFiles);
        MainGenerator generator = new MainGenerator("xtextt", context);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void testXtext() {
//        List<XtextFile> allXtextFiles = findAllFiles();
//        MetaContext context = new MetaContextImpl(allXtextFiles);
//        MainGenerator generator = new MainGenerator("xtext", context);
//        try {
//            generator.generate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    //
    public void testExpressions() {
        List<XtextFile> allXtextFiles = findAllFiles();
        MetaContext context = new MetaContextImpl(allXtextFiles);
        MainGenerator generator = new MainGenerator("expr", context);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    public void testSmallJava() {
        List<XtextFile> allXtextFiles = findAllFiles();
        MetaContext context = new MetaContextImpl(allXtextFiles);
        MainGenerator generator = new MainGenerator("smalljava", context);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testSmallJavaCopy() {
        List<XtextFile> allXtextFiles = findAllFiles();
        MetaContext context = new MetaContextImpl(allXtextFiles);
        MainGenerator generator = new MainGenerator("smalljava2", context);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testEntityLan() {
        List<XtextFile> allXtextFiles = findAllFiles();
        MetaContext context = new MetaContextImpl(allXtextFiles);
        MainGenerator generator = new MainGenerator("entity", context);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //
    public void testStatemachine() {
        List<XtextFile> allXtextFiles = findAllFiles();
        MetaContext context = new MetaContextImpl(allXtextFiles);
        MainGenerator generator = new MainGenerator("stat", context);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void testArithmetics() {
        List<XtextFile> allXtextFiles = findAllFiles();
        MetaContext context = new MetaContextImpl(allXtextFiles);
        MainGenerator generator = new MainGenerator("calc", context);
        try {
            generator.generate();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //==========================================Old commented tests

    //    public void testComputingRules1() throws IOException {
//        PsiFile file = getXtextFile();
//        XtextFile xtextFile = (XtextFile) file;
//        XtextMainModel model = new XtextMainModel(xtextFile);
//
//
//        assertTrue(model.getMyParserRules().size() == 2);
//    }
//
////    public void testTerminalRules() throws IOException {
////        PsiFile file = getXtextFile();
////        XtextFile xtextFile = (XtextFile) file;
////
////        XtextMainModel fileModel = new XtextMainModel(xtextFile);
////        BnfGeneratorOld generator = new BnfGeneratorOld("newGrammar", fileModel);
////        assertEquals("^?([a-z]|[A-Z]|$|_)([a-z]|[A-Z]|$|_|[0-9])*", generator.getRegexpAsString(fileModel.getTerminalRuleByName("ID").getMyRule()));
////        assertEquals(".", generator.getRegexpAsString(fileModel.getTerminalRuleByName("ANY_OTHER").getMyRule()));
////        assertEquals("( |\\t|\\r|\\n)+ref_STRING.", generator.getRegexpAsString(fileModel.getTerminalRuleByName("WITH_REF").getMyRule()));
////    }
////
////    public void testParserRules() {
////        PsiFile file = getXtextFile();
////        XtextFile xtextFile = (XtextFile) file;
////
////        XtextMainModel fileModel = new XtextMainModel(xtextFile);
////        BnfGeneratorOld generator = new BnfGeneratorOld("newGrammar", fileModel);
////
////        assertEquals("'term' | C | REFERENCE_RUL ", generator.getRuleAlternativesAsString(fileModel.getParserRuleByName("B").getMyRule()));
////    }
//
////    public void testParserRules2() {
////        PsiFile file = getXtextFile();
////        XtextFile xtextFile = (XtextFile) file;
////
////        XtextMainModel fileModel = new XtextMainModel(xtextFile);
////        BnfGeneratorOld generator = new BnfGeneratorOld("newGrammar", fileModel);
////
////        assertEquals("XImportSection ? AbstractElement * ", generator.getRuleAlternativesAsString(fileModel.getParserRuleByName("Domainmodel").getMyRule()));
////
////        assertEquals("PackageDeclaration | Entity ", generator.getRuleAlternativesAsString(fileModel.getParserRuleByName("AbstractElement").getMyRule()));
////
////        assertEquals("'entity' ValidID ('extends' JvmTypeReference ) ? '{' Feature * '}' ", generator.getRuleAlternativesAsString(fileModel.getParserRuleByName("Entity").getMyRule()));
////
////        assertEquals("ValidID ':' JvmTypeReference ", generator.getRuleAlternativesAsString(fileModel.getParserRuleByName("Property").getMyRule()));
////
////        assertEquals("'op' ValidID '(' (FullJvmFormalParameter (',' FullJvmFormalParameter ) * ) ? ')' ':' JvmTypeReference XBlockExpression ", generator.getRuleAlternativesAsString(fileModel.getParserRuleByName("Operation").getMyRule()));
////
////    }
//
//    public void testGeneration1() {
//        PsiFile file = getXtextFile();
//         XtextFile xtextFile = (XtextFile) file;
//        XtextMainModel fileModel = new XtextMainModel(xtextFile);
//        Generator generator = new Generator("newGrammar", fileModel);
//        XtextParserRule r = XtextElementFactory.createParserRule("A : B ;");
//        try {
//            generator.generate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        assertEquals("Operation", fileModel.getParserRuleByName("RuleFromFeature_OPERATION").getReturnType());
//
//
//    }
//

//    public void testGeneration2() {
//        PsiFile file = getXtextFile();
//        XtextFile xtextFile = (XtextFile) file;
//        XtextMainModel model = new XtextMainModel(xtextFile);
//        Generator generator = new Generator("newGrammar", model);
//        try {
//            generator.generate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    public void testReturnsAndFragment() {
//        PsiFile file = getXtextFile();
//        XtextFile xtextFile = (XtextFile) file;
//        XtextMainModel model = new XtextMainModel(xtextFile);
//        assertEquals("JvmTypeReference", model.getParserRuleByName("JvmArgumentTypeReference").getReturnType());
//        assertEquals(null, model.getParserRuleByName("JvmWildcardTypeReference").getReturnType());
//        assertEquals("JvmLowerBound", model.getParserRuleByName("JvmLowerBoundAnded").getReturnType());
//        assertTrue(model.getParserRuleByName("A").isFragment());
//        assertTrue(model.getTerminalRuleByName("B").isFragment());
//        assertFalse(model.getParserRuleByName("JvmWildcardTypeReference").isFragment());
//
//    }
//
//    public void testNegatedTerminalRules() {
//        PsiFile file = getXtextFile();
//        XtextFile xtextFile = (XtextFile) file;
//        XtextMainModel model = new XtextMainModel(xtextFile);
//        Generator generator = new Generator("newGrammar", model);
////
//        assertEquals("[^l]", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG1").getMyRule()));
//        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG2").getMyRule()));
//        assertEquals("[^l-d]", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG3").getMyRule()));
//        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG4").getMyRule()));
//        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG5").getMyRule()));
//        assertEquals("[^a\\na-z]", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG6").getMyRule()));
//        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG7").getMyRule()));
//        assertEquals("[^l]string", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG8").getMyRule()));
//        assertEquals("[^l]( |\\t|\\r|\\n)+[^l-d]", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG9").getMyRule()));
//        assertEquals("", generator.getGeneratorUtil().getRegexpAsString(model.getTerminalRuleByName("NEG10").getMyRule()));
//
//    }
//
//    public void testGenerationImportedGrammar() {
//        PsiFile file = getXtextFile();
//        XtextFile xtextFile = (XtextFile) file;
//        XtextMainModel model = new XtextMainModel(xtextFile);
//        BuildModelWithImports(model, model);
//        Generator generator = new Generator("newGrammar", model);
//        try {
//            generator.generate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assertNotNull(model.getParserRuleByName("Domainmodel"));
//        assertNotNull(model.getParserRuleByName("Entity"));
//        assertNotNull(model.getParserRuleByName("Feature"));
//        assertNotNull(model.getParserRuleByName("Property"));
//
//        assertNotNull(model.getParserRuleByName("JvmTypeReference"));
//        assertNotNull(model.getParserRuleByName("ArrayBrackets"));
//        assertNotNull(model.getParserRuleByName("XFunctionTypeRef"));
//        assertNotNull(model.getParserRuleByName("JvmParameterizedTypeReference"));
//        assertNotNull(model.getParserRuleByName("JvmArgumentTypeReference"));
//        assertNotNull(model.getParserRuleByName("JvmWildcardTypeReference"));
//        assertNotNull(model.getParserRuleByName("JvmUpperBound"));
//        assertNotNull(model.getParserRuleByName("JvmUpperBoundAnded"));
//        assertNotNull(model.getParserRuleByName("JvmLowerBound"));
//        assertNotNull(model.getParserRuleByName("JvmLowerBoundAnded"));
//        assertNotNull(model.getParserRuleByName("JvmTypeParameter"));
//        assertNotNull(model.getParserRuleByName("QualifiedName"));
//        assertNotNull(model.getParserRuleByName("QualifiedNameWithWildcard"));
//        assertNotNull(model.getParserRuleByName("ValidID"));
//        assertNotNull(model.getParserRuleByName("XImportSection"));
//        assertNotNull(model.getParserRuleByName("XImportDeclaration"));
//        assertNotNull(model.getParserRuleByName("QualifiedNameInStaticImport"));
//
//
//    }
//
//    public void testGenerationImportedGrammar2() {
//        PsiFile file = getXtextFile();
//        XtextFile xtextFile = (XtextFile) file;
//        XtextMainModel model = new XtextMainModel(xtextFile);
//        BuildModelWithImports(model, model);
//        Generator generator = new Generator("newGrammar", model);
//        try {
//            generator.generate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        assertNotNull(model.getParserRuleByName("Domainmodel"));
//        assertNotNull(model.getParserRuleByName("Entity"));
//        assertNotNull(model.getParserRuleByName("Feature"));
//        assertNotNull(model.getParserRuleByName("Property"));
//        assertNotNull(model.getParserRuleByName("AbstractElement"));
//        assertNotNull(model.getParserRuleByName("PackageDeclaration"));
//        assertNotNull(model.getParserRuleByName("Operation"));
//        assertNotNull(model.getParserRuleByName("JvmParameterizedTypeReference"));
//        assertNotNull(model.getParserRuleByName("JvmArgumentTypeReference"));
//        assertNotNull(model.getParserRuleByName("JvmWildcardTypeReference"));
//        assertNotNull(model.getParserRuleByName("JvmUpperBound"));
//        assertNotNull(model.getParserRuleByName("JvmUpperBoundAnded"));
//        assertNotNull(model.getParserRuleByName("JvmLowerBound"));
//        assertNotNull(model.getParserRuleByName("JvmLowerBoundAnded"));
//        assertNotNull(model.getParserRuleByName("JvmTypeParameter"));
//        assertNotNull(model.getParserRuleByName("QualifiedName"));
//        assertNotNull(model.getParserRuleByName("QualifiedNameWithWildcard"));
//        assertNotNull(model.getParserRuleByName("ValidID"));
//        assertNotNull(model.getParserRuleByName("XImportSection"));
//        assertNotNull(model.getParserRuleByName("XImportDeclaration"));
//        assertNotNull(model.getParserRuleByName("QualifiedNameInStaticImport"));
//
//        assertNotNull(model.getParserRuleByName("XExpression"));
//        assertNotNull(model.getParserRuleByName("XAssignment"));
//        assertNotNull(model.getParserRuleByName("OpSingleAssign"));
//        assertNotNull(model.getParserRuleByName("OpMultiAssign"));
//        assertNotNull(model.getParserRuleByName("XOrExpression"));
//        assertNotNull(model.getParserRuleByName("OpOr"));
//        assertNotNull(model.getParserRuleByName("XAndExpression"));
//        assertNotNull(model.getParserRuleByName("OpAnd"));
//        assertNotNull(model.getParserRuleByName("XEqualityExpression"));
//        assertNotNull(model.getParserRuleByName("OpEquality"));
//        assertNotNull(model.getParserRuleByName("XRelationalExpression"));
//        assertNotNull(model.getParserRuleByName("OpCompare"));
//        assertNotNull(model.getParserRuleByName("XOtherOperatorExpression"));
//        assertNotNull(model.getParserRuleByName("OpOther"));
//
//    }
//
////    public void testXtext() {
////        PsiFile file = getXtextFile();
////        XtextFile xtextFile = (XtextFile) file;
////        XtextMainModel fileModel = new XtextMainModel(xtextFile);
////        BuildModelWithImports(fileModel, fileModel);
////        fileModel.createVisitorGeneratorModel();
////        Generator generator = new Generator("newXtext", fileModel);
////        try {
////            generator.generate();
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////
////
////    }


//    public void testEntityExampleReferencesImplementation() {
//        EntityFile entityFile = getEntityFile("entityLanguage");
//        EmfObjectFactory factory = new EmfObjectFactory();
//        Domainmodel domainmodel = factory.createEmfModel(Objects.requireNonNull(PsiTreeUtil.findChildOfType(entityFile, EntityDomainmodel.class)));
//        Resource.Factory.Registry reg = Resource.Factory.Registry.INSTANCE;
//        Map<String, Object> m = reg.getExtensionToFactoryMap();
//        m.put("dmodel", new XMIResourceFactoryImpl());
//
//        // Obtain a new resource set
//        ResourceSet resSet = new ResourceSetImpl();
//
//        Resource resource = resSet.createResource(URI
//                .createURI("My2.dmodel"));
//        // Get the first model element and cast it to the right type, in my
//        // example everything is hierarchical included in this first node
//        resource.getContents().add(domainmodel);
//        // now save the content.
//        try {
//            resource.save(Collections.EMPTY_MAP);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//        List<XtextFile> allXtextFiles = findAllFiles();
//        XtextMainModel model = new XtextMainModel(allXtextFiles);
//
//        Generator generator = new Generator("Entity", model);
//        try {
//            generator.generate();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
////
//
//    }





}
