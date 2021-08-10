package com.intellij.xtext.language;

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
import com.intellij.xtext.language.parser.XtextParser;
import com.intellij.xtext.language.psi.XtextFile;
import com.intellij.xtext.language.psi.XtextTypes;
import org.jetbrains.annotations.NotNull;

public class XtextParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet KEYWORDS = TokenSet.create(
            XtextTypes.GRAMMAR_KEYWORD,
            XtextTypes.WITH_KEYWORD,
            XtextTypes.HIDDEN_KEYWORD,
            XtextTypes.GENERATE_KEYWORD,
            XtextTypes.AS_KEYWORD,
            XtextTypes.IMPORT_KEYWORD,
            XtextTypes.FRAGMENT_KEYWORD,
            XtextTypes.RETURNS_KEYWORD,
            XtextTypes.CURRENT_KEYWORD,
            XtextTypes.TRUE_KEYWORD,
            XtextTypes.FALSE_KEYWORD,
            XtextTypes.TERMINAL_KEYWORD,
            XtextTypes.EOF_KEYWORD,
            XtextTypes.ENUM_KEYWORD);
    public static final TokenSet COMMENTS = TokenSet.create(XtextTypes.SL_COMMENT, XtextTypes.ML_COMMENT);
    public static final IFileElementType FILE = new IFileElementType(XtextLanguage.INSTANCE);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new XtextLexerAdapter();
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
        return new XtextParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new XtextFile(viewProvider);
    }

    public SpaceRequirements spaceExistenceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return XtextTypes.Factory.createElement(node);
    }
}
