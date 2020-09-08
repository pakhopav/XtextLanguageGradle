package com.intellij.calcLanguage.calc;

import com.intellij.calcLanguage.calc.parser.CalcParser;
import com.intellij.calcLanguage.calc.psi.CalcFile;
import com.intellij.calcLanguage.calc.psi.CalcTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class CalcParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet KEYWORDS = TokenSet.create(
            CalcTypes.MODULE_KEYWORD,
            CalcTypes.IMPORT_KEYWORD,
            CalcTypes.DEF_KEYWORD);
    public static final TokenSet COMMENTS = TokenSet.create(CalcTypes.SL_COMMENT, CalcTypes.ML_COMMENT);
    public static final IFileElementType FILE = new IFileElementType(CalcLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new CalcLexerAdapter();
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }


    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new CalcParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new CalcFile(viewProvider);
    }

    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return CalcTypes.Factory.createElement(node);
    }
}
