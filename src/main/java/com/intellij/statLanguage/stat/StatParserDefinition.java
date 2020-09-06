package com.intellij.statLanguage.stat;

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
import com.intellij.statLanguage.stat.parser.StatParser;
import com.intellij.statLanguage.stat.psi.StatFile;
import com.intellij.statLanguage.stat.psi.StatTypes;
import org.jetbrains.annotations.NotNull;

public class StatParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet KEYWORDS = TokenSet.create();
    public static final TokenSet COMMENTS = TokenSet.create(StatTypes.SL_COMMENT, StatTypes.ML_COMMENT);
    public static final IFileElementType FILE = new IFileElementType(StatLanguage.INSTANCE);
            
    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new StatLexerAdapter();
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
        return new StatParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new StatFile(viewProvider);
    }

    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return StatTypes.Factory.createElement(node);
    }
}