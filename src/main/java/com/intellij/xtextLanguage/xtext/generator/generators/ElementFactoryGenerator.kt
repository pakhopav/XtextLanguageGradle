package com.intellij.xtextLanguage.xtext.generator.generators


import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import com.intellij.xtextLanguage.xtext.generator.models.elements.names.NameGenerator
import java.io.FileOutputStream
import java.io.PrintWriter

class ElementFactoryGenerator(extension: String, val context: MetaContext, rootPath: String) :
    AbstractGenerator(extension, rootPath) {
    val relevantRules = context.rules

    fun generateElementFactoryFile() {
        val file = createOrFindFile("${extensionCapitalized}ElementFactory.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.println(
            """
        |package $packageDir.psi;
        |
        |import $packageDir.${extensionCapitalized}Language;
        |import $packageDir.${extensionCapitalized}LexerAdapter;
        |import $packageDir.${extensionCapitalized}ParserDefinition;
        |import $packageDir.parser.${extensionCapitalized}Parser;
        |import com.intellij.lang.ASTNode;
        |import com.intellij.lang.Language;
        |import com.intellij.lang.PsiBuilder;
        |import com.intellij.lang.PsiBuilderFactory;
        |import com.intellij.openapi.application.ReadAction;
        |import com.intellij.openapi.project.Project;
        |import com.intellij.psi.PsiElement;
        |import com.intellij.psi.PsiFile;
        |import com.intellij.psi.PsiFileFactory;
        |import com.intellij.psi.tree.IElementType;
        |import com.intellij.psi.util.PsiTreeUtil;
        |import org.jetbrains.annotations.Nullable;
        |import java.util.Optional;

        |public abstract class ${extensionCapitalized}ElementFactory {
        |
        |    private static Language language = ${extensionCapitalized}Language.INSTANCE;
        |    
        |    public static <T> T createSynthetic(String text, IElementType type, Class<T> expectedClass) {
        |        PsiBuilderFactory factory = PsiBuilderFactory.getInstance();
        |        PsiBuilder psiBuilder = factory.createBuilder(new ${extensionCapitalized}ParserDefinition(), new ${extensionCapitalized}LexerAdapter(), text);
        |        ${extensionCapitalized}Parser parser = new ${extensionCapitalized}Parser();
        |        parser.parseLight(type, psiBuilder);
        |        ASTNode astNode = ReadAction.compute(psiBuilder::getTreeBuilt);
        |        PsiElement psiResult = ${extensionCapitalized}Types.Factory.createElement(astNode);
        |        if (PsiTreeUtil.hasErrorElements(psiResult)) {
        |            return null;
        |        }
        |        return expectedClass.isInstance(psiResult) ? expectedClass.cast(psiResult) : null;
        |    }
        |    
        |    @Nullable
        |    public static <T> T createElement(String elementText, IElementType type, Class<T> expectedClass, Project project) {
        |        PsiFileFactory factory = PsiFileFactory.getInstance(project);
        |        PsiElement psiResult = null;
        """.trimMargin("|"))
        out.print(relevantRules.map { "if (type == ${extensionCapitalized}Types.${NameGenerator.toGKitTypesName(it.name)}) {\n            psiResult = create${extensionCapitalized}${NameGenerator.toGKitClassName(it.name)}(elementText, factory);\n" }
                .joinToString(prefix = "        ", separator = "        } else ", postfix = "}"))
        out.println("""
        |        return (expectedClass.isInstance(psiResult))? expectedClass.cast(psiResult) : null;
        |    }
        """.trimMargin("|"))
//        relevantRules.forEach {
//            generateCreateMethodForRule(it, out)
//        }
        out.println("""
        |    private static PsiElement findFirstChildOfType(PsiElement psiElement, IElementType type){
        |        if(psiElement.getNode().getElementType() == type) return psiElement;
        |        PsiElement searchResult;
        |        for(PsiElement child : psiElement.getChildren()){
        |            searchResult = findFirstChildOfType(child, type);
        |            if(searchResult != null){
        |                return searchResult;
        |            }
        |        }
        |        return null;
        |    }
        """.trimIndent())
        out.print("}")
        out.close()
    }

//    private fun generateCreateMethodForRule(rule: TreeRule, out: PrintWriter){
//        val complement = context.getValidFileComplement(rule)!!
//
//        out.println("""
//        |    private static PsiElement create${extensionCapitalized}${NameGenerator.toGKitClassName(rule.name)}(String elementText, PsiFileFactory factory) {
//        |        String newFileText = "${complement.prefix}" + elementText + "${complement.postfix}";
//        |        PsiFile newFile = factory.createFileFromText(language , newFileText);
//        |        PsiElement newElement =  Optional.ofNullable(findFirstChildOfType(newFile, ${extensionCapitalized}Types${NameGenerator.toGKitTypesName(rule.name)})).orElse(null);
//        |        return newElement;
//        |    }
//        """.trimMargin("|"))
//    }
//
//    private fun filterRelevantParserRules(): List<TreeRule>{
//        val allRules = mutableListOf<TreeRule>()
//        allRules.addAll(context.rules)
//        allRules.addAll(context.terminalRules)
//        return allRules.filter { context.getValidFileComplement(it) != null }
//    }
}