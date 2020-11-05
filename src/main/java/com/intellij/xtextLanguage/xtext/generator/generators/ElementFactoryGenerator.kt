package com.intellij.xtextLanguage.xtext.generator.generators


import com.intellij.xtextLanguage.xtext.generator.models.MetaContext
import java.io.FileOutputStream
import java.io.PrintWriter

class ElementFactoryGenerator(extension: String, val context: MetaContext) : AbstractGenerator(extension) {

    fun generateElementFactoryFile() {
        val file = createFile("${extensionCapitalized}ElementFactory.java", myGenDir + "/psi")
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir.psi;
            |
            |import com.intellij.calc2Language.calc2.Calc2LexerAdapter;
            |import com.intellij.calc2Language.calc2.Calc2ParserDefinition;
            |import com.intellij.calc2Language.calc2.parser.Calc2Parser;
            |import com.intellij.lang.ASTNode;
            |import com.intellij.lang.PsiBuilder;
            |import com.intellij.lang.PsiBuilderFactory;
            |import com.intellij.openapi.application.ReadAction;
            |import com.intellij.psi.PsiElement;
            |import com.intellij.psi.tree.IElementType;
            |import com.intellij.psi.util.PsiTreeUtil;
            |import com.sun.istack.Nullable;
            |import com.intellij.psi.impl.source.tree.LeafPsiElement;
            |
            |public abstract class ${extensionCapitalized}ElementFactory {
            |
            |    @Nullable
            |    public static LeafPsiElement createLeafFromString(String text, IElementType type) {
            |        LeafPsiElement psiResult = new LeafPsiElement(type, text);
            |        if (PsiTreeUtil.hasErrorElements(psiResult)) {
            |            return null;
            |        }
            |        return psiResult;
            |    }
            |    
            |    public static <T> T parseFromString(String text, IElementType type, Class<T> expectedClass) {
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
            |}

        """.trimMargin("|"))
        out.close()
    }
}