package com.intellij.xtextLanguage.xtext.generator.generators

import com.intellij.xtextLanguage.xtext.generator.models.XtextMainModel
import java.io.FileOutputStream
import java.io.PrintWriter

class ParserDefinitionFileGenerator(extension: String, val fileModel: XtextMainModel) : AbstractGenerator(extension) {
    fun generateParserDefinitionFile() {
        val file = createFile(extension.capitalize() + "ParserDefinition.java", myGenDir)
        val out = PrintWriter(FileOutputStream(file))
        out.println("""
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
            |import $packageDir.parser.${extension.capitalize()}Parser;
            |import $packageDir.psi.${extension.capitalize()}Types;
            |import $packageDir.psi.${extension.capitalize()}File;
            |import org.jetbrains.annotations.NotNull;
            
            |public class ${extension.capitalize()}ParserDefinition implements ParserDefinition {
            |    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
            |    public static final TokenSet KEYWORDS = TokenSet.create(
            """.trimMargin("|"))

        fileModel.keywordModel.keywordsForParserDefinitionFile.forEach {
            out.print("            ${extension.capitalize()}Types.${it.name}")
            if (it != fileModel.keywordModel.keywordsForParserDefinitionFile.last()) out.print(",\n")

        }
        out.print(");\n")
        if (fileModel.ruleResolver.getTerminalRuleByName("ML_COMMENT") != null && fileModel.ruleResolver.getTerminalRuleByName("SL_COMMENT") != null) {
            out.print("    public static final TokenSet COMMENTS = TokenSet.create(${extension.capitalize()}Types.SL_COMMENT, ${extension.capitalize()}Types.ML_COMMENT);")
        } else out.print("    public static final TokenSet COMMENTS = null;")
        out.print("""
            |    public static final IFileElementType FILE = new IFileElementType(${extension.capitalize()}Language.INSTANCE);
            
            |    @NotNull
            |    @Override
            |    public Lexer createLexer(Project project) {
            |        return new ${extension.capitalize()}LexerAdapter();
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
            |        return new ${extension.capitalize()}Parser();
            |    }
            
            |    @Override
            |    public IFileElementType getFileNodeType() {
            |        return FILE;
            |    }
            
            |    public PsiFile createFile(FileViewProvider viewProvider) {
            |        return new ${extension.capitalize()}File(viewProvider);
            |    }
            
            |    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
            |        return SpaceRequirements.MAY;
            |    }
            
            |    @NotNull
            |    public PsiElement createElement(ASTNode node) {
            |        return ${extension.capitalize()}Types.Factory.createElement(node);
            |    }
            |}

        """.trimMargin("|"))
        out.close()

    }
}