package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class ParserDefinitionFileGenerator(extention: String, fileModel: XtextMainModel) : Generator(extention, fileModel) {
    fun generateParserDefinitionFile() {
        val file = createFile(extention.capitalize() + "ParserDefinition.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.print("""
            |package $packageDir;
            |import com.intellij.lang.ASTNode;
            |import com.intellij.lang.ParserDefinition;
            |import com.intellij.lang.PsiParser;
            |import com.intellij.lexer.Lexer;
            |import com.intellij.openapi.project.Project;
            |import com.intellij.psi.FileViewProvider;
            |import com.intellij.psi.PsiElement;
            |import com.intellij.psi.PsiFile;
            |import com.intellij.psi.TokenType;
            |import com.intellij.psi.tree.IFileElementType;
            |import com.intellij.psi.tree.TokenSet;
            |import $packageDir.parser.${extention.capitalize()}Parser;
            |import $packageDir.psi.${extention.capitalize()}Types;
            |import $packageDir.psi.${extention.capitalize()}File;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extention.capitalize()}ParserDefinition implements ParserDefinition {
            |    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
            |    public static final TokenSet KEYWORDS = TokenSet.create(
            """.trimMargin("|"))

        fileModel.keywordModel.keywordsForParserDefinitionFile.forEach {
            out.print("    ${extention.capitalize()}Types.${it}")
            if (it != fileModel.keywordModel.keywordsForParserDefinitionFile.last()) out.print(",\n")

        }
        out.print(");\n")
        if (fileModel.ruleResolver.getTerminalRuleByName("ML_COMMENT") != null && fileModel.ruleResolver.getTerminalRuleByName("SL_COMMENT") != null) {
            out.print("    public static final TokenSet COMMENTS = TokenSet.create(${extention.capitalize()}Types.SL_COMMENT, ${extention.capitalize()}Types.ML_COMMENT);")
        } else out.print("    public static final TokenSet COMMENTS = null;")
        out.print("""
            |    public static final IFileElementType FILE = new IFileElementType(${extention.capitalize()}Language.INSTANCE);
            
            |    @NotNull
            |    @Override
            |    public Lexer createLexer(Project project) {
            |        return new ${extention.capitalize()}LexerAdapter();
            |    }
            
            |    @NotNull
            |    public TokenSet getWhitespaceTokens() {
            |         return WHITE_SPACES;
            |    }
            |    @NotNull
            |    public TokenSet getCommentTokens() {
            |        return COMMENTS;
            |    }
            
            
            
            |    @NotNull
            |    public TokenSet getStringLiteralElements() {
            |        return TokenSet.EMPTY;
            |    }
            
            |    @NotNull
            |    public PsiParser createParser(final Project project) {
            |        return new ${extention.capitalize()}Parser();
            |    }
            
            |    @Override
            |    public IFileElementType getFileNodeType() {
            |        return FILE;
            |    }
            
            |    public PsiFile createFile(FileViewProvider viewProvider) {
            |        return new ${extention.capitalize()}File(viewProvider);
            |    }
            
            |    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
            |        return SpaceRequirements.MAY;
            |    }
            
            |    @NotNull
            |    public PsiElement createElement(ASTNode node) {
            |        return ${extention.capitalize()}Types.Factory.createElement(node);
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}